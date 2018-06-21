package cn.edu.nju.software.ui.bizservice.impl;

import cn.edu.nju.software.common.pojo.*;
import cn.edu.nju.software.common.util.DateUtil;
import cn.edu.nju.software.fabricservice.protomsg.Persistence;

/**
 * @author Daniel
 * @since 2018/5/2 14:44
 */
public class BizConverter {
    public static TracingItemInfo convertTracingItemInfo(Persistence.ItemAsset itemAsset) {
        TracingItemInfo tracingItemInfo = new TracingItemInfo();
        tracingItemInfo.setTime(DateUtil.formatDate(itemAsset.getTimestamp()));
        tracingItemInfo.setEnvStatus(convertEnvStatus(itemAsset.getEvnStatus()));
        tracingItemInfo.setOperationStatus(convertOperationStatus(itemAsset.getOpsStatus()));
        tracingItemInfo.setItemInfo(convertItemInfo(itemAsset.getItemInfo()));
        tracingItemInfo.setItemState(convertItemStatus(itemAsset.getItemStatus()));
        tracingItemInfo.setItemId(itemAsset.getItemId());
        return tracingItemInfo;
    }

    public static AddressInfo convertAddressInfo(Persistence.Address address) {
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setNodeName(address.getName());
        addressInfo.setAddressDesc(address.getDesc());
        addressInfo.setLongtitude(address.getLongitude());
        addressInfo.setLatitude(address.getLatitude());
        return addressInfo;
    }

    public static EnvStatus convertEnvStatus(Persistence.EnvStatus envStatus) {
        EnvStatus re = new EnvStatus();
        AddressInfo addressInfo = convertAddressInfo(envStatus.getAddress());
        re.setAddressInfo(addressInfo);
        return re;
    }

    public static OperationStatus convertOperationStatus(Persistence.OpsStatus opsStatus) {
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setContactWay(opsStatus.getContactWay());
        operationStatus.setExtraInfo(opsStatus.getExtraInfo());
        operationStatus.setOrganization(opsStatus.getCurrentOrg());
        operationStatus.setLastOrganization(opsStatus.getLastOrg());
        operationStatus.setOpType(OpType.valueOf(opsStatus.getOpType().toString()));
        return operationStatus;

    }

    public static ItemInfo convertItemInfo(Persistence.ItemInfo itemInfo) {
        ItemInfo itemInfo1 = new ItemInfo();
        itemInfo1.setName(itemInfo.getName());
        itemInfo1.setClass_(itemInfo.getClass_());
        itemInfo1.setBatchNum(itemInfo.getBatchNumber());
        itemInfo1.setManufactureDate(DateUtil.formatDate(itemInfo.getManufactureDate()));
        itemInfo1.setNote(itemInfo.getNote());
        return itemInfo1;
    }

    public static ItemStatus convertItemStatus(Persistence.ItemStatus itemStatus) {
        ItemStatus itemStatus1 = new ItemStatus();
        itemStatus1.setLogs(itemStatus.getLogs());
        itemStatus1.setNormal(itemStatus.getNormal());
        return itemStatus1;
    }


}
