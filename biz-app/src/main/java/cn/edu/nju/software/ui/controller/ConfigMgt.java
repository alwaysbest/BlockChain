package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.common.pojo.bizservice.response.PeerStateResponse;
import cn.edu.nju.software.common.pojo.bizservice.request.UIPeerAddRequest;
import cn.edu.nju.software.fabricservice.configmgt.ChaincodeConfig;
import cn.edu.nju.software.fabricservice.configmgt.HFConfig;
import cn.edu.nju.software.fabricservice.serviceinvoker.InvokeContext;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Daniel
 * @since 2018/5/2 15:21
 */
@RestController
@Api("配置管理")
public class ConfigMgt {

    @Autowired
    ServiceInvoker serviceInvoker;

    @RequestMapping(value = "/config", method = RequestMethod.POST)
    @ApiOperation(value = "获得当前所有的配置信息")
    public BizResponse<HFConfig> getTracingItemInfo() {
        return BizResponse.createSuccess(serviceInvoker.getConfigMgt().getConfig(), "success");
    }

    @RequestMapping(value = "/peer/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加一个Peer")
    public BizResponse addPeer(@RequestBody UIPeerAddRequest uiPeerAddRequest) {
        return null;
    }

    @RequestMapping(value = "/peers", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有Peer的状态信息")
    public BizResponse<List<PeerStateResponse>> getPeerStates() {
        HFConfig hfConfig = serviceInvoker.getConfigMgt().getConfig();
        Set<String> peers = new HashSet<>();
        for (ChaincodeConfig chaincodeConfig : hfConfig.getChaincodes()) {
            peers.addAll(chaincodeConfig.getPeers());
        }
        List<PeerStateResponse> peerStateResponses = new ArrayList<>();
        for (String peer : peers) {
            PeerStateResponse peerStateResponse = new PeerStateResponse();
            peerStateResponse.setPeerName(peer);
            peerStateResponse.setPeerUrl(serviceInvoker.getHfClientHelper().getPEERS().get(peer)
                    .getUrl());
            boolean pingState = pingPeer(peer);
            peerStateResponse.setState(pingState);
            System.out.println(pingState);
            peerStateResponse.setCurrentInvoke(serviceInvoker.getServiceDiscovery().currentInvoke.get(peer));
            peerStateResponse.setTotalInvoke(serviceInvoker.getServiceDiscovery().totalInvoke.get
                    (peer));
            peerStateResponses.add(peerStateResponse);
        }
        return BizResponse.createSuccess(peerStateResponses, "success");
    }

    @RequestMapping(value = "/request/info", method = RequestMethod.POST)
    @ApiOperation(value = "获得当前组织发起的共识情况")
    public BizResponse getAllRequestInfo() {
        return null;
    }

    public boolean pingPeer(String peerName) {
        InvokeContext invokeContext = new InvokeContext();
        invokeContext.setPeerNames(Arrays.asList(peerName));
        return serviceInvoker.pingContext(invokeContext);
    }


}
