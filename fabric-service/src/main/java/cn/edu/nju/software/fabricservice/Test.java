package cn.edu.nju.software.fabricservice;

import cn.edu.nju.software.fabricservice.configmgt.HFConfig;
import cn.edu.nju.software.fabricservice.protomsg.Persistence;
import cn.edu.nju.software.fabricservice.protomsg.Requests;
import cn.edu.nju.software.fabricservice.protomsg.ResponseOuterClass;
import cn.edu.nju.software.fabricservice.serviceinvoker.HFClientHelper;
import com.google.gson.Gson;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.ProposalResponse;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static cn.edu.nju.software.fabricservice.serviceinvoker.HFClientHelper.*;

/**
 * @author Daniel
 * @since 2018/5/2 0:07
 */
public class Test {
    public static void main(String[] args) throws Exception {

        String chainCodeName = "myCC";
        Collection<ProposalResponse> proposalResponses;
        Collection<ProposalResponse> responses;

        HFClientHelper hfClientHelper = new HFClientHelper();
        hfClientHelper.init(HFConfig.newInstance());

        //——————————————————————————————添加商品信息————————————————————————————————————————————

        Persistence.Address address = Persistence.Address.newBuilder().setName("add1")
                .setLongitude(10.0).setLatitude(10.1).build();
        Persistence.ItemInfo itemInfo = Persistence.ItemInfo.newBuilder().setName("item1")
                .setBatchNumber("2018111").setClass_("cmp").setNote("note").build();
        Requests.ItemAddRequest itemAddRequest = Requests.ItemAddRequest.newBuilder().setAddress
                (address).setItemInfo(itemInfo).setItemId("12345678901234567890123456789012")
                .build();
        responses = hfClientHelper.chainCodeInvoke(chainCodeName, "1.0", "addItem",
                itemAddRequest.toByteArray());
        for (ProposalResponse proposalResponse : responses) {
            if (proposalResponse.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                System.out.println(proposalResponse.getMessage());
                byte[] result = proposalResponse.getChaincodeActionResponsePayload();
                ResponseOuterClass.Response response = ResponseOuterClass.Response.parseFrom(result);
                System.out.println(response.getMessage());
            } else {
                System.out.println("response fail:" + proposalResponse.getMessage());
            }
        }

        hfClientHelper.sendTransactions(responses);


//        sendTransactions(responses).thenAccept(e -> {
//            System.out.println("catch tx events!!!");
//            System.out.println(e.getTransactionID());
//            System.out.println(e.getCreator().getId());
//            System.out.println(e.getBlockEvent().getBlockNumber());
//        }).get();
        //——————————————————————————————END————————————————————————————————————————————
        TimeUnit.SECONDS.sleep(5);


        //——————————————————————————————END————————————————————————————————————————————

        //——————————————————————————————查询商品信息————————————————————————————————————————————
        {
            proposalResponses = hfClientHelper.chainCodeQuery(chainCodeName, "1.0", "getItem",
                    Requests.ItemGetRequest.newBuilder().setItemId
                            ("12345678901234567890123456789012")
                            .setHistData(false).build().toByteArray());

            for (ProposalResponse proposalResponse : proposalResponses) {
                System.out.println(proposalResponse.getMessage());
                byte[] result = proposalResponse.getChaincodeActionResponsePayload();
                ResponseOuterClass.Response response = ResponseOuterClass.Response.parseFrom(result);
                System.out.println(response.getMessage());
                Persistence.ItemAsset itemAsset = Persistence.ItemAsset.parseFrom(response.getData());
                System.out.println(itemAsset.toString());
            }
        }

        //——————————————————————————————改变商品信息————————————————————————————————————————————

        Persistence.Address address1 = Persistence.Address.newBuilder().setName("add2")
                .setLongitude(10.0).setLatitude(10.1).build();
        Persistence.EnvStatus envStatus = Persistence.EnvStatus.newBuilder().setAddress(address1)
                .build();
        Persistence.ItemStatus itemStatus = Persistence.ItemStatus.newBuilder()
                .setNormal(true).setLogs("这是测试111").build();
        Requests.ItemChangeRequest itemChangeRequest = Requests.ItemChangeRequest.newBuilder()
                .setOpType(Persistence.OPType.DEPARTURE).setEnvStatus(envStatus).setItemStatus
                        (itemStatus).setItemId("12345678901234567890123456789012").build();
        responses = hfClientHelper.chainCodeInvoke(chainCodeName, "1.0", "changeItem",
                itemChangeRequest.toByteArray());
        for (ProposalResponse proposalResponse : responses) {
            System.out.println(proposalResponse.getMessage());
            byte[] result = proposalResponse.getChaincodeActionResponsePayload();
            ResponseOuterClass.Response response = ResponseOuterClass.Response.parseFrom(result);
            System.out.println(response.getMessage());
        }

        hfClientHelper.sendTransactions(responses);
        //——————————————————————————————END————————————————————————————————————————————
        TimeUnit.SECONDS.sleep(5);

        {
            proposalResponses = hfClientHelper.chainCodeQuery(chainCodeName, "1.0", "getItem",
                    Requests.ItemGetRequest.newBuilder().setItemId
                            ("12345678901234567890123456789012")
                            .setHistData(true).build().toByteArray());

            for (ProposalResponse proposalResponse : proposalResponses) {
                System.out.println(proposalResponse.getMessage());
                byte[] result = proposalResponse.getChaincodeActionResponsePayload();
                ResponseOuterClass.Response response = ResponseOuterClass.Response.parseFrom(result);
                System.out.println(response.getMessage());
                ResponseOuterClass.ItemGetResponse itemGetResponse = ResponseOuterClass
                        .ItemGetResponse.parseFrom(response.getData());
                System.out.println(new Gson().toJson(itemGetResponse.getItemAssetsList()));
            }
        }

    }
}
