package cn.edu.nju.software.fabricservice.serviceinvoker;

import cn.edu.nju.software.fabricservice.bean.SampleUser;
import cn.edu.nju.software.fabricservice.configmgt.HFConfig;
import cn.edu.nju.software.fabricservice.protomsg.Requests;
import cn.edu.nju.software.fabricservice.protomsg.ResponseOuterClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.edu.nju.software.common.util.LoggerUtil.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel
 * @since 2018/4/30 23:52
 * 对SDK的进一步封装，通过配置文件简化了配置细节
 */
public class HFClientHelper {
    private final Logger logger = LoggerFactory.getLogger(HFClientHelper.class);
    private HFClient hfClient;
    private HFCAClient hfCaClient;
    private HFConfig hfconfig;

    //channels
    @Getter
    private Map<String, Channel> CHANNELS;
    @Getter
    private Map<String, SampleUser> USERS;
    @Getter
    private Map<String, Peer> PEERS;

    static Channel DEFAULT_CHANNEL, CURRENT_CHANNEL;
    private SampleUser DEFAULT_USER, CURRENT_USER;
    private Collection<Peer> CURRENT_PEERS;


    public void init(HFConfig hfConfig) {
        this.hfconfig = hfConfig;

        CHANNELS = new HashMap<>();
        USERS = new HashMap<>();
        PEERS = new HashMap<>();

        hfClient = HFClient.createNewInstance();

        try {
            hfClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        } catch (Exception e) {
            logger.error("get crypto suite error");
            e.printStackTrace();
        }

        config();
    }


    /**
     * 配置
     */
    void config() {
        //配置CA服务器
        try {
            hfCaClient = HFCAClient.createNewInstance(hfconfig.getCaConfig().getCaUrl(), null);
            hfCaClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        } catch (MalformedURLException e) {
        } catch (Exception e) {
            errorf(logger, "error create hf CA client");
            System.exit(1);
        }
        configUser();

        configChannel();

    }

    /**
     * 配置channel
     */
    void configChannel() {
        //配置channel
        hfconfig.getChannels().forEach((chconfig) -> {
            String cname = chconfig.getChannelName();
            try {
                Channel channel = hfClient.newChannel(cname);
                //添加peer
                chconfig.getPeers().forEach((pname, purl) -> {
                    try {
                        Peer peer = hfClient.newPeer(pname, "grpc://" + purl);
                        PEERS.put(pname, peer);
                        channel.addPeer(peer);
                    } catch (InvalidArgumentException e) {
                        errorf(logger, "error create peer,name=%s,url=%s", pname, purl);
                    }
                });

                Properties ordererProperties = new Properties();
                //example of setting keepAlive to avoid timeouts on inactive http2 connections.
                // Under 5 minutes would require changes to server side to accept faster ping rates.
                ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTime", new
                        Object[]{20L, TimeUnit.MINUTES});
                ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveTimeout", new
                        Object[]{20L, TimeUnit.SECONDS});
                ordererProperties.put("grpc.NettyChannelBuilderOption.keepAliveWithoutCalls", new Object[]{true});

                //添加orderer
                chconfig.getOrderers().forEach((oname, ourl) -> {
                    try {

                        channel.addOrderer(hfClient.newOrderer(oname, "grpc://" + ourl, ordererProperties));
                    } catch (InvalidArgumentException e) {
                        errorf(logger, "error create orderer,name=%s,url=%s", oname, ourl);
                    }
                });

                //——————————————————————添加eventhub
                Properties eventHubProperties = new Properties();
                eventHubProperties.put("grpc.NettyChannelBuilderOption.keepAliveTime", new Object[]{5L, TimeUnit.MINUTES});
                eventHubProperties.put("grpc.NettyChannelBuilderOption.keepAliveTimeout", new Object[]{8L, TimeUnit.SECONDS});
                chconfig.getEventhubs().forEach((ename, eurl) -> {
                    EventHub eventHub = null;
                    try {
                        eventHub = hfClient.newEventHub(ename, "grpc://" + eurl, eventHubProperties);
                        channel.addEventHub(eventHub);
                    } catch (InvalidArgumentException e) {
                        errorf(logger, "error add eventhub %s", ename);
                        e.printStackTrace();
                    }
                });


                try {
                    //初始化channel
                    channel.initialize();
                    //存入map中
                    CHANNELS.put(cname, channel);
                } catch (TransactionException e) {
                    errorf(logger, "error initial channel %s", cname);
                    e.printStackTrace();
                }
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
                errorf(logger, "error create channel,name=%s", cname);
            }
        });

        //没有可用的channel
        if (CHANNELS.isEmpty()) {
            errorf(logger, "no channel available");
            System.exit(1);
        }

        DEFAULT_CHANNEL = CHANNELS.get(hfconfig.getDefaultChannel());

        CURRENT_CHANNEL = DEFAULT_CHANNEL;

        CURRENT_PEERS = CURRENT_CHANNEL.getPeers();

        if (DEFAULT_CHANNEL == null)
            errorf(logger, "can't find the default channel: %s", hfconfig.getDefaultChannel());

    }

    void configUser() {
        hfconfig.getUsers().forEach(userConfig -> {
            SampleUser sampleUser = new SampleUser(userConfig.getUsername(), userConfig.getOrg(),
                    null);
            sampleUser.setMspId(userConfig.getMspId());
            sampleUser.setEnrollmentSecret(userConfig.getPassword());

            try {
                sampleUser.setEnrollment(hfCaClient.enroll(userConfig.getUsername(), userConfig.getPassword()));
            } catch (EnrollmentException e) {
                e.printStackTrace();
            } catch (org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException e) {
                e.printStackTrace();
            }

            USERS.put(userConfig.getUsername(), sampleUser);
        });

        if (USERS.isEmpty()) {
            errorf(logger, "no user available");
            System.exit(1);
        }

        DEFAULT_USER = USERS.get(hfconfig.getDefaultUser());

        if (DEFAULT_USER == null) {
            String userName = USERS.keySet().iterator().next();
            DEFAULT_USER = USERS.get(userName);
        }

        CURRENT_USER = DEFAULT_USER;

        try {
            hfClient.setUserContext(DEFAULT_USER);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }


    public Enrollment enroll(String username, String password) {
        try {
            return hfCaClient.enroll(username, password);
        } catch (EnrollmentException e) {
            e.printStackTrace();
        } catch (org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String registerUser(String username, String org) {
        try {
            RegistrationRequest rr = new RegistrationRequest(username, org);
            String key = hfCaClient.register(rr, USERS.get("admin"));
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean ping() {
        String pingInfo = "PING_MSG";
        Collection<ProposalResponse> responses = chainCodeInvoke(hfconfig.getCcName(), hfconfig
                .getCcVersion(), "ping", pingInfo.getBytes());
        for (ProposalResponse proposalResponse : responses) {
            if (proposalResponse.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                try {
                    String msg = new String(proposalResponse.getChaincodeActionResponsePayload());
                    if (msg.equals(pingInfo))
                        return true;
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public void setContext(InvokeContext invokeContext) {
        if (invokeContext != null) {
            if (invokeContext.getChannelName() != null && CHANNELS.containsKey(invokeContext.getChannelName())) {
                CURRENT_CHANNEL = CHANNELS.get(invokeContext.getChannelName());
            } else {
                CURRENT_CHANNEL = DEFAULT_CHANNEL;
            }
            if (invokeContext.getSampleUser() != null) {
                CURRENT_USER = invokeContext.getSampleUser();
            }
            if (invokeContext.getPeerNames() != null) {
                List<Peer> peers = new ArrayList<>();
                for (String peer : invokeContext.getPeerNames()) {
                    peers.add(PEERS.get(peer));
                }
                CURRENT_PEERS = peers;
            }
        }
        if (CURRENT_USER != null) {
            try {
                hfClient.setUserContext(CURRENT_USER);
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
        if (CURRENT_PEERS == null || CURRENT_PEERS.isEmpty()) {
            CURRENT_PEERS = CURRENT_CHANNEL.getPeers();
        }
    }

    public Collection<ProposalResponse> chainCodeInvoke(String chaincodeName, String chaincodeVersion, String func, byte[] data) {

        ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion)
                .build();
        TransactionProposalRequest transactionProposalRequest = hfClient.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setFcn(func);
        transactionProposalRequest.setProposalWaitTime(200000);
        if (data != null)
            transactionProposalRequest.setArgs(data);

        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));

        try {
            transactionProposalRequest.setTransientMap(tm2);
            Collection<ProposalResponse> transactionPropResp =
                    CURRENT_CHANNEL.sendTransactionProposal(transactionProposalRequest, CURRENT_PEERS);
            return transactionPropResp;
        } catch (ProposalException | InvalidArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CompletableFuture<BlockEvent.TransactionEvent> sendTransactions(Collection<ProposalResponse> responses) {
        return CURRENT_CHANNEL.sendTransaction(responses);
    }


    public Collection<ProposalResponse> chainCodeQuery(String chaincodeName, String chaincodeVersion, String func, byte[] data) {
        ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion)
                .build();
        QueryByChaincodeRequest queryByChaincodeRequest = hfClient.newQueryProposalRequest();
        queryByChaincodeRequest.setFcn(func);
        if (data != null)
            queryByChaincodeRequest.setArgs(data);
        queryByChaincodeRequest.setChaincodeID(chaincodeID);
        Collection<ProposalResponse> queryProposals;
        try {
            queryProposals = CURRENT_CHANNEL.queryByChaincode(queryByChaincodeRequest, CURRENT_PEERS);
        } catch (Exception e) {
            throw new CompletionException(e);
        }
        return queryProposals;
    }


    public static void main(String[] args) throws Exception {


        Collection<ProposalResponse> proposalResponses;
        Requests.SimpleRequest simpleRequest;
        Collection<ProposalResponse> responses;

        HFClientHelper hfClientHelper = new HFClientHelper();
        hfClientHelper.init(HFConfig.newInstance());

        simpleRequest = Requests.SimpleRequest.newBuilder().setName
                ("daniel").setDes("des").build();
        responses = hfClientHelper.chainCodeInvoke("myCC", "1.0", "set",
                simpleRequest.toByteArray());
        hfClientHelper.sendTransactions(responses);

        System.out.println(responses.iterator().next().getTransactionID());

        simpleRequest = Requests.SimpleRequest.newBuilder().setName
                ("daniel").setDes("des1").build();
        responses = hfClientHelper.chainCodeInvoke("myCC", "1.0", "set",
                simpleRequest.toByteArray());
        hfClientHelper.sendTransactions(responses);

        System.out.println(responses.iterator().next().getTransactionID());

        TimeUnit.SECONDS.sleep(5);

        proposalResponses = hfClientHelper.chainCodeQuery("myCC", "1.0", "get",
                "daniel".getBytes());

        for (ProposalResponse proposalResponse : proposalResponses) {
            System.out.println(proposalResponse.getMessage());
            byte[] result = proposalResponse.getChaincodeActionResponsePayload();
            Requests.SimpleRequest simpleRequest1 = Requests.SimpleRequest.parseFrom(result);
            System.out.println(simpleRequest1.toString());
        }

        proposalResponses = hfClientHelper.chainCodeQuery("myCC", "1.0", "getAllHist",
                "daniel".getBytes());

        for (ProposalResponse proposalResponse : proposalResponses) {
            System.out.println(proposalResponse.getMessage());
            byte[] result = proposalResponse.getChaincodeActionResponsePayload();
            ResponseOuterClass.SimpleResponse simpleRequest1 = ResponseOuterClass.SimpleResponse.parseFrom(result);
            System.out.println(simpleRequest1.toString());
        }

//
//
        BlockchainInfo channelInfo = DEFAULT_CHANNEL.queryBlockchainInfo();
        System.out.println("Channel info for : " + DEFAULT_CHANNEL.getName());
        System.out.println("Channel height: " + channelInfo.getHeight());
        String chainCurrentHash = Hex.encodeHexString(channelInfo.getCurrentBlockHash());
        String chainPreviousHash = Hex.encodeHexString(channelInfo.getPreviousBlockHash());
        System.out.println("Chain current block hash: " + chainCurrentHash);
        System.out.println("Chainl previous block hash: " + chainPreviousHash);
    }


}


