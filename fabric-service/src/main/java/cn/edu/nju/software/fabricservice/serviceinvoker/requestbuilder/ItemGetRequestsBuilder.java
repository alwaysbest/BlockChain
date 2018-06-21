package cn.edu.nju.software.fabricservice.serviceinvoker.requestbuilder;

import cn.edu.nju.software.fabricservice.protomsg.Requests;

/**
 * @author Daniel
 * @since 2018/5/2 14:36
 */
public class ItemGetRequestsBuilder {
    private String itemId;
    private boolean histData;
    private boolean addData;

    public ItemGetRequestsBuilder setAddData(boolean addData) {
        this.addData = addData;
        return this;
    }

    public ItemGetRequestsBuilder setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemGetRequestsBuilder setHistData(boolean histData) {
        this.histData = histData;
        return this;
    }

    public Requests.ItemGetRequest build() {
        return Requests.ItemGetRequest.newBuilder().setItemId(itemId).setHistData(histData)
                .setAllData(addData).build();
    }

}
