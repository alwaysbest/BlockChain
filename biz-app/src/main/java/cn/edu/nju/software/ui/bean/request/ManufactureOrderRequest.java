package cn.edu.nju.software.ui.bean.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import cn.edu.nju.software.ui.bean.ManufactureOrderType;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/13 23:21
 */
@Data
public class ManufactureOrderRequest {
    AddressInfo addressInfo;
    ManufactureOrderType orderType;
    Integer orgId;
    String departureAddress;
    //商品编号，逗号或空格隔开
    String items;
}
