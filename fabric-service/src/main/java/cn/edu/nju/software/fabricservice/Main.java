package cn.edu.nju.software.fabricservice;

import cn.edu.nju.software.fabricservice.bean.SampleStore;
import cn.edu.nju.software.fabricservice.bean.SampleUser;
import cn.edu.nju.software.common.util.ReflectionUtil;
import cn.edu.nju.software.fabricservice.protomsg.Persistence;
import cn.edu.nju.software.fabricservice.protomsg.Requests;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 这是测试sdk的基本功能
 */
public class Main {
    public static void main(String[] args) throws Exception {

        HFClient hfClient = HFClient.createNewInstance();
        hfClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        File sampleStoreFile = new File("d:/HFCSampletest" +
                ".properties");
        if (sampleStoreFile.exists()) { //For testing start fresh
            sampleStoreFile.delete();
        }

        final SampleStore sampleStore = new SampleStore(sampleStoreFile);

        HFCAClient caClient = HFCAClient.createNewInstance("http://192.168.136.131:7054", null);
        caClient.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());

        SampleUser admin = new SampleUser("admin", "org1", sampleStore);
        admin.setMspId("Org1MSP");

        admin.setEnrollment(caClient.enroll(admin.getName(), "adminpw"));
//
//        SampleUser user1 = new SampleUser("new-user1", "org1.department", sampleStore);

//        RegistrationRequest rr = new RegistrationRequest(user1.getName(), "org1.department1");
//        String enrollmentSecret = caClient.register(rr, admin);
//        System.out.println("register successful, secret:" + enrollmentSecret);
//        user1.setEnrollmentSecret(enrollmentSecret);

//        user1.setEnrollmentSecret("kvLBfKDcleqB");
//
//        user1.setEnrollment(caClient.enroll(user1.getName(), user1.getEnrollmentSecret()));
//
//        user1.setMspId("Org1MSP");
//
//        System.out.println(user1.getEnrollment().getKey().toString());
//        System.out.println(user1.getEnrollment().getCert());


        ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("myCC3").setVersion("1.0")
                .build();


        hfClient.setUserContext(admin);
        Channel channel = hfClient.newChannel("mychannel");
        Peer peer = hfClient.newPeer("peer1", "grpc://192.168.136.131:7051");
        Orderer orderer = hfClient.newOrderer("order1", "grpc://192.168.136.131:7050");
        channel.addPeer(peer);
        channel.addOrderer(orderer);


        //用反射工具设置初始化，不然会报错，感觉是当前的一个Bug
        ReflectionUtil.setField(channel, "initialized", true);

//
//        InstantiateProposalRequest instantiateProposalRequest = hfClient
//                .newInstantiationProposalRequest();
//        instantiateProposalRequest.setProposalWaitTime(100000);
//        instantiateProposalRequest.setChaincodeID(chaincodeID);
//        instantiateProposalRequest.setFcn("init");
//        instantiateProposalRequest.setArgs("a", "{\"Name\":\"com\",\"Description\":\"dec1\"}");
//        Map<String, byte[]> tm = new HashMap<>();
//        tm.put("HyperLedgerFabric", "InstantiateProposalRequest:JavaSDK".getBytes(UTF_8));
//        tm.put("method", "InstantiateProposalRequest".getBytes(UTF_8));
//        instantiateProposalRequest.setTransientMap(tm);
//
//        ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
//        chaincodeEndorsementPolicy.fromStream(Main.class.getResourceAsStream("/endorsementpolicy" +
//                ".yaml"));
//        instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);
//
//        Collection<ProposalResponse> responses = channel.sendInstantiationProposal
//                (instantiateProposalRequest);
//        for (ProposalResponse response : responses) {
//            if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
//                System.out.printf("Succesful instantiate proposal response Txid: %s from peer " +
//                        "%s", response.getTransactionID(), response.getPeer().getName());
//            } else {
//                System.out.println("Instantiate error" + response.getMessage());
//            }
//        }

        hfClient.setUserContext(admin);

        //

//        TransactionProposalRequest transactionProposalRequest = hfClient.newTransactionProposalRequest();
//        transactionProposalRequest.setChaincodeID(chaincodeID);
//        transactionProposalRequest.setFcn("set");
//        transactionProposalRequest.setProposalWaitTime(600000);
//        transactionProposalRequest.setArgs("b", "10");
//
//        Map<String, byte[]> tm2 = new HashMap<>();
//        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); //Just some extra junk in transient map
//        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//        tm2.put("result", ":)".getBytes(UTF_8));  // This should be returned see chaincode why.
//
//        transactionProposalRequest.setTransientMap(tm2);
//
//
//        Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
//        for (ProposalResponse response : transactionPropResp) {
//            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
//                System.out.println(response.getMessage());
//                System.out.printf("Successful transaction proposal response Txid: %s from peer " +
//                        "%s\n", response
//                        .getTransactionID(), response.getPeer().getName());
//            } else {
//                System.out.println("error");
//            }
//        }
//
//        //必须要发送到Orderer上，不然没办法更新
//        channel.sendTransaction(transactionPropResp);
//
//
//        QueryByChaincodeRequest queryByChaincodeRequest1 = hfClient.newQueryProposalRequest();
//        queryByChaincodeRequest1.setFcn("get");
//        queryByChaincodeRequest1.setArgs("b");
//        queryByChaincodeRequest1.setChaincodeID(chaincodeID);
//
//
//        Collection<ProposalResponse> queryProposals1;
//
//        try {
//            queryProposals1 = channel.queryByChaincode(queryByChaincodeRequest1);
//        } catch (Exception e) {
//            throw new CompletionException(e);
//        }
//
//        for (ProposalResponse proposalResponse : queryProposals1) {
//            System.out.println(proposalResponse.getMessage());
//            System.out.println(new String(proposalResponse.getChaincodeActionResponsePayload(),
//                    Charsets.UTF_8));
//        }


        // Send transaction proposal to all peers
        TransactionProposalRequest transactionProposalRequest = hfClient.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chaincodeID);
        transactionProposalRequest.setFcn("set");
        transactionProposalRequest.setProposalWaitTime(200000);
        Requests.SimpleRequest simpleRequest = Requests.SimpleRequest.newBuilder().setName
                ("daniel").setDes("des").build();
        System.out.println(simpleRequest.toString());
        transactionProposalRequest.setArgs(simpleRequest.toByteArray());

        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); //Just some extra junk in transient map
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
        tm2.put("result", ":)".getBytes(UTF_8));  // This should be returned see chaincode why.

        transactionProposalRequest.setTransientMap(tm2);


        Collection<ProposalResponse> transactionPropResp = channel.sendTransactionProposal(transactionProposalRequest, channel.getPeers());
        for (ProposalResponse response : transactionPropResp) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                System.out.printf("Successful transaction proposal response Txid: %s from peer " +
                        "%s\n", response
                        .getTransactionID(), response.getPeer().getName());
            } else {
                System.out.println(response.getMessage());
                System.out.println("error");
            }
        }

        channel.sendTransaction(transactionPropResp);


        QueryByChaincodeRequest queryByChaincodeRequest1 = hfClient.newQueryProposalRequest();
        queryByChaincodeRequest1.setFcn("get");
        queryByChaincodeRequest1.setArgs("daniel".getBytes());
        queryByChaincodeRequest1.setChaincodeID(chaincodeID);


        Collection<ProposalResponse> queryProposals1;

        try {
            queryProposals1 = channel.queryByChaincode(queryByChaincodeRequest1);
        } catch (Exception e) {
            throw new CompletionException(e);
        }

        for (ProposalResponse proposalResponse : queryProposals1) {
            System.out.println(proposalResponse.getMessage());
            byte[] result = proposalResponse.getChaincodeActionResponsePayload();
            Requests.SimpleRequest simpleRequest1 = Requests.SimpleRequest.parseFrom(result);
            System.out.println(simpleRequest1.toString());
        }

//
//
//        BlockchainInfo channelInfo = channel.queryBlockchainInfo();
//        System.out.println("Channel info for : " + channel.getName());
//        System.out.println("Channel height: " + channelInfo.getHeight());
//        String chainCurrentHash = Hex.encodeHexString(channelInfo.getCurrentBlockHash());
//        String chainPreviousHash = Hex.encodeHexString(channelInfo.getPreviousBlockHash());
//        System.out.println("Chain current block hash: " + chainCurrentHash);
//        System.out.println("Chainl previous block hash: " + chainPreviousHash);


    }

}
