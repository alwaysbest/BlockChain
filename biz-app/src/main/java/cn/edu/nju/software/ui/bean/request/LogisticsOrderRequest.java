package cn.edu.nju.software.ui.bean.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/13 23:23
 */
@Data
public class LogisticsOrderRequest {
    AddressInfo addressInfo;
    String orderDesc;
    String departureAddress;
    String items;
}
