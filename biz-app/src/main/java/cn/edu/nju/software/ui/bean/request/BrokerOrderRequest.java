package cn.edu.nju.software.ui.bean.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/14 16:11
 */
@Data
public class BrokerOrderRequest {
    AddressInfo addressInfo;
    String itemIds;
}
