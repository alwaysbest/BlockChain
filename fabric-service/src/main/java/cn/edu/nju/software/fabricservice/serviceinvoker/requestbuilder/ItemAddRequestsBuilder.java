package cn.edu.nju.software.fabricservice.serviceinvoker.requestbuilder;

import cn.edu.nju.software.fabricservice.protomsg.Persistence;
import cn.edu.nju.software.fabricservice.protomsg.Requests;

/**
 * @author Daniel
 * @since 2018/5/2 14:36
 */
public class ItemAddRequestsBuilder {
    String addressName;
    double longitude;
    double latitude;
    String itemName;
    String batchNum;
    String class_;
    String note;
    String itemId;
    String addressDesc;
    Long manufactureDate;
    String contact;


    public ItemAddRequestsBuilder setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
        return this;
    }

    public ItemAddRequestsBuilder setAddressName(String addressName) {
        this.addressName = addressName;
        return this;
    }

    public ItemAddRequestsBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public ItemAddRequestsBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public ItemAddRequestsBuilder setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public ItemAddRequestsBuilder setBatchNum(String batchNum) {
        this.batchNum = batchNum;
        return this;
    }

    public ItemAddRequestsBuilder setClass_(String class_) {
        this.class_ = class_;
        return this;
    }

    public ItemAddRequestsBuilder setNote(String note) {
        this.note = note;
        return this;
    }

    public ItemAddRequestsBuilder setManufactureDate(Long manufactureDate) {
        this.manufactureDate = manufactureDate;
        return this;
    }

    public ItemAddRequestsBuilder setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public Requests.ItemAddRequest build() {
        Persistence.Address address = Persistence.Address.newBuilder().setName(addressName)
                .setLongitude(longitude).setLatitude(latitude).setDesc(addressDesc).build();
        Persistence.ItemInfo itemInfo = Persistence.ItemInfo.newBuilder().setName(itemName)
                .setBatchNumber(batchNum).setClass_(class_).setNote(note).setManufactureDate
                        (manufactureDate).build();
        Requests.ItemAddRequest itemAddRequest = Requests.ItemAddRequest.newBuilder()
                .setAddress(address).setItemInfo(itemInfo).setItemId(itemId).build();
        return itemAddRequest;
    }

}
