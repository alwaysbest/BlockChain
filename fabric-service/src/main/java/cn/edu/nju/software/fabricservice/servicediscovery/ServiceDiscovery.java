package cn.edu.nju.software.fabricservice.servicediscovery;

import cn.edu.nju.software.common.util.MathUtil;
import cn.edu.nju.software.fabricservice.configmgt.ChaincodeConfig;
import cn.edu.nju.software.fabricservice.configmgt.HFConfig;
import cn.edu.nju.software.fabricservice.serviceinvoker.InvokeContext;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvokerId;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Daniel
 * @since 2018/5/7 18:31
 * 服务发现模块是针对用户发起的服务请求，自动的匹配到提供相应服务的节点，并将请求发送到该节点上执行。
 * 匹配节点时可以按照一定的负载均衡策略进行匹配，从而提高整个系统的资源利用率，增大系统的可靠性与性能。
 */
public class ServiceDiscovery {

    @Setter
    ServiceInvoker serviceInvoker;

    public Map<String, Integer> totalInvoke;
    public Map<String, Integer> currentInvoke;

    List<ChaincodeConfig> chaincodeConfigs;

    Map<String, List<String>> chainCodeIdPeers;
    Map<String, Integer> currentIndex;
    Map<String, String> chaincodeUsers;
    Map<String, String> chaincodeChannels;

    Set<String> peers;

    public void init(HFConfig hfConfig) {
        peers = new HashSet<>();
        currentInvoke = new ConcurrentHashMap<>();
        totalInvoke = new ConcurrentHashMap<>();

        this.chaincodeConfigs = hfConfig.getChaincodes();
        chainCodeIdPeers = new HashMap<>();
        currentIndex = new HashMap<>();
        chaincodeUsers = new HashMap<>();
        chaincodeChannels = new HashMap<>();
        for (ChaincodeConfig chaincodeConfig : chaincodeConfigs) {
            for (String func : chaincodeConfig.getFunc()) {
                ServiceInvokerId serviceInvokerId = new ServiceInvokerId(chaincodeConfig.getName(),
                        chaincodeConfig.getVersion(), func);
                String id = serviceInvokerId.getId();
                chainCodeIdPeers.put(id, chaincodeConfig.getPeers());
                peers.addAll(chaincodeConfig.getPeers());
                currentIndex.put(id, 0);
                chaincodeUsers.put(id, chaincodeConfig.getUser());
                chaincodeChannels.put(id, chaincodeConfig.getChannel());
            }
        }

        for (String peer : peers) {
            totalInvoke.put(peer, 0);
            currentInvoke.put(peer, 0);
        }
    }

    public void peerInvoke(String peerName) {
        int invoke = totalInvoke.get(peerName);
        totalInvoke.put(peerName, invoke + 1);
        int current = currentInvoke.get(peerName);
        currentInvoke.put(peerName, current + 1);
    }

    public void peerInvokeEnd(String peerName) {
        int current = currentInvoke.get(peerName);
        currentInvoke.put(peerName, current - 1);
    }


    /**
     * 根据指定的服务标识，返回可以执行服务的节点地址配置
     *
     * @param serviceInvokerId
     * @return
     */
    public InvokeContext findService(ServiceInvokerId serviceInvokerId, DiscoveryParas discoveryParas) {
        String id = serviceInvokerId.getId();
        List<String> peers = chainCodeIdPeers.get(id);
        Integer index = currentIndex.get(id);

        int peerSize = peers.size();

        InvokeContext invokeContext = new InvokeContext();
        invokeContext.setChannelName(chaincodeUsers.get(id));

        List<String> choosedPeers = null;

        //当节点数量小于需求数量时，直接设置为全部节点
        if (discoveryParas.getPeerNum() > peerSize) {
            choosedPeers = peers;
        } else {
            switch (discoveryParas.getLoadBalanceType()) {
                case RANDOM:
                    //随机选择
                    List<Integer> indexes = MathUtil.random(discoveryParas.getPeerNum(), peerSize,
                            true);
                    if (indexes == null)
                        return null;
                    choosedPeers = indexes.stream().map(peers::get).collect(Collectors.toList());
                    break;
                case POLLING:
                    //轮询
                    choosedPeers = new ArrayList<>();
                    for (int i = 0; i < discoveryParas.getPeerNum(); i++) {
                        if (index >= peerSize) {
                            index -= peerSize;
                        }
                        choosedPeers.add(peers.get(index));
                        index++;
                    }
                    currentIndex.put(id, index);
                    break;
                case INVOKE_LOAD:
                    choosedPeers = new ArrayList<>();
                    peers.sort((e1, e2) -> {
                        int b1 = currentInvoke.get(e1);
                        int b2 = currentIndex.get(e2);
                        return Integer.compare(b1, b2);
                    });
                    for (int i = 0; i < discoveryParas.getPeerNum(); i++) {
                        choosedPeers.add(peers.get(0));
                    }
            }

        }
        invokeContext.setPeerNames(choosedPeers);
        return invokeContext;
    }

    List<String> randomPeer(int peerNum, List<String> peers) {
        int peerSize = peers.size();
        List<String> rePeers = new ArrayList<>();
        for (int i = 0; i < peerNum; i++) {
            while (true) {
                int tem = (int) (Math.random() * peerSize);
                String peer = peers.get(tem);
                if (rePeers.contains(peer))
                    continue;
                rePeers.add(peer);
                break;
            }
        }
        return rePeers;
    }

    int currentPollingIndex = 0;

    List<String> pollingPeer(int peerNum, List<String> peers) {
        int peerSize = peers.size();
        List<String> rePeers = new ArrayList<>();
        for (int i = 0; i < peerNum; i++) {
            if (currentPollingIndex >= peerSize)
                currentPollingIndex -= peerSize;
            rePeers.add(peers.get(currentPollingIndex));
        }
        return rePeers;
    }

    List<String> minReqeustPeer(int peerNum, List<String> peers) {
        List<String> choosedPeers = new ArrayList<>();
        peers.sort((e1, e2) -> {
            int b1 = currentInvoke.get(e1);
            int b2 = currentIndex.get(e2);
            return Integer.compare(b1, b2);
        });
        for (int i = 0; i < peerNum; i++) {
            choosedPeers.add(peers.get(0));
        }
        return choosedPeers;
    }

}
