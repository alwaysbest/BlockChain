package cn.edu.nju.software.ui.bizservice.impl;

import cn.edu.nju.software.common.pojo.*;
import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.common.pojo.bizservice.request.UIItemAddRequest;
import cn.edu.nju.software.common.pojo.bizservice.request.UIItemChangeRequest;
import cn.edu.nju.software.common.pojo.bizservice.request.UIItemGetRequest;
import cn.edu.nju.software.common.pojo.fabricservice.FSResponse;
import cn.edu.nju.software.common.util.DateUtil;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvokerId;
import cn.edu.nju.software.fabricservice.serviceinvoker.requestbuilder.RequestsBuilder;
import cn.edu.nju.software.fabricservice.protomsg.Requests;
import cn.edu.nju.software.fabricservice.protomsg.ResponseOuterClass;
import cn.edu.nju.software.ui.bizservice.ItemTracingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Daniel
 * @since 2018/5/2 14:15
 */
@Service
public class ItemTracingServiceImpl implements ItemTracingService {
    @Autowired
    ServiceInvoker serviceInvoker;

    @Override
    public BizResponse<List<TracingItemInfo>> getTracingItemInfo(UIItemGetRequest uiItemGetRequest) {
        Requests.ItemGetRequest getRequest = RequestsBuilder.newItemGetRequestsBuilder()
                .setItemId(uiItemGetRequest.getItemId())
                .setAddData(uiItemGetRequest.isAllItem())
                .setHistData(uiItemGetRequest.isHistData()).build();
        FSResponse<Object> resp = serviceInvoker.invoke(ServiceInvokerId.ITEM_GET,
                getRequest, null);
        if (!RespStatus.SUCCESS_CODE.equals(resp.respStatus.getCode()))
            return BizResponse.createWithoutData(-1, "error invoke with message:%s", resp
                    .getRespStatus().getMsg());
        ResponseOuterClass.ItemGetResponse itemAsset = (ResponseOuterClass.ItemGetResponse) resp.getData();
        if (itemAsset.getItemAssetsList() == null) {
            return BizResponse.createWithoutData(-1, "null result");
        }
        List<TracingItemInfo> tracingItemInfos = itemAsset.getItemAssetsList().stream().map
                (BizConverter::convertTracingItemInfo).collect(Collectors.toList());

        return BizResponse.createSuccess(tracingItemInfos, "success");
    }


    @Override
    public BizResponse addItemTracingInfo(UIItemAddRequest uiItemAddRequest) {
        ItemInfo item = uiItemAddRequest.getItemInfo();
        AddressInfo addressInfo = uiItemAddRequest.getAddressInfo();
        Requests.ItemAddRequest itemAddRequest = RequestsBuilder.newItemAddRequestBuilder()
                .setItemId(uiItemAddRequest.getItemId())
                .setItemName(item.getName())
                .setBatchNum(item.getBatchNum())
                .setClass_(item.getClass_())
                .setManufactureDate(DateUtil.parseDate(item.getManufactureDate()).getTime())
                .setNote(item.getNote())
                .setAddressName(addressInfo.getNodeName())
                .setAddressDesc(addressInfo.getAddressDesc())
                .setLongitude(addressInfo.getLongtitude())
                .setLatitude(addressInfo.getLatitude())
                .setAddressDesc(uiItemAddRequest.getAddressInfo().getAddressDesc())
                .build();
        FSResponse response = serviceInvoker.invoke(ServiceInvokerId.ITEM_ADD, itemAddRequest,
                null);
        if (!response.getRespStatus().isSuccess()) {
            return BizResponse.createWithoutData(-1, response.getRespStatus().getMsg());
        }
        return BizResponse.createSuccess(null, "success");
    }

    @Override
    public BizResponse changeItemTracingInfo(UIItemChangeRequest uiItemChangeRequest) {
        ItemStatus itemStatus = uiItemChangeRequest.getItemStatus();
        EnvStatus envStatus = uiItemChangeRequest.getEnvStatus();
        AddressInfo addressInfo = envStatus.getAddressInfo();
        Requests.ItemChangeRequest itemChangeRequest = RequestsBuilder.newItemChangeRequestsBuilder()
                .setItemId(uiItemChangeRequest.getItemId())
                .setAddressName(addressInfo.getNodeName())
                .setAddressDesc(addressInfo.getAddressDesc())
                .setLongitude(addressInfo.getLongtitude())
                .setLatitude(addressInfo.getLatitude())
                .setAddressDesc(addressInfo.getAddressDesc())
                .setLogs(itemStatus.getLogs())
                .setOpType(OpType.getOpTypeByIndex(uiItemChangeRequest.getOpType()))
                .setContact(uiItemChangeRequest.getContact())
                .setNextOrg(uiItemChangeRequest.getNextOrg())
                .setExtraInfo(uiItemChangeRequest.getExtraInfo())
                .setNormal(itemStatus.isNormal())
                .build();
        FSResponse response = serviceInvoker.invoke(ServiceInvokerId.ITEM_CHANGE, itemChangeRequest,
                null);
        if (!response.getRespStatus().isSuccess()) {
            return BizResponse.createWithoutData(-1, response.getRespStatus().getMsg());
        }
        return BizResponse.createSuccess(null, "success");
    }
}
