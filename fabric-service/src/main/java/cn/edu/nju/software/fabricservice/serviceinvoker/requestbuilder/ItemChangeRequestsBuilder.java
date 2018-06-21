package cn.edu.nju.software.fabricservice.serviceinvoker.requestbuilder;

import cn.edu.nju.software.common.pojo.OpType;
import cn.edu.nju.software.fabricservice.protomsg.Persistence;
import cn.edu.nju.software.fabricservice.protomsg.Requests;

/**
 * @author Daniel
 * @since 2018/5/2 20:01
 */
public class ItemChangeRequestsBuilder {
    String addressName;
    String addressDesc;
    double longitude;
    double latitude;
    String itemId;
    boolean normal;
    String logs;
    OpType opType;
    String contact;
    String nextOrg;
    String extraInfo;

    public ItemChangeRequestsBuilder setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
        return this;
    }

    public ItemChangeRequestsBuilder setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public ItemChangeRequestsBuilder setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public ItemChangeRequestsBuilder setNextOrg(String nextOrg) {
        this.nextOrg = nextOrg;
        return this;
    }

    public ItemChangeRequestsBuilder setAddressName(String addressName) {
        this.addressName = addressName;
        return this;
    }

    public ItemChangeRequestsBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public ItemChangeRequestsBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public ItemChangeRequestsBuilder setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }


    public ItemChangeRequestsBuilder setNormal(boolean normal) {
        this.normal = normal;
        return this;
    }

    public ItemChangeRequestsBuilder setLogs(String logs) {
        this.logs = logs;
        return this;
    }

    public ItemChangeRequestsBuilder setOpType(OpType opType) {
        this.opType = opType;
        return this;
    }

    public Requests.ItemChangeRequest build() {
        Persistence.Address address = Persistence.Address.newBuilder().setName(addressName)
                .setLongitude(longitude).setLatitude(latitude).setDesc(addressDesc).build();
        Persistence.EnvStatus envStatus = Persistence.EnvStatus.newBuilder().setAddress(address)
                .build();
        Persistence.ItemStatus itemStatus = Persistence.ItemStatus.newBuilder().setNormal(normal)
                .setLogs(logs).build();
        Requests.ItemChangeRequest itemChangeRequest = Requests.ItemChangeRequest.newBuilder()
                .setEnvStatus(envStatus).setItemStatus(itemStatus).setOpType(Persistence.OPType
                        .valueOf(opType.toString())).setItemId(itemId).setNextOrg(nextOrg)
                .setExtraInfo(extraInfo).setContact(contact).build();
        return itemChangeRequest;
    }

}
