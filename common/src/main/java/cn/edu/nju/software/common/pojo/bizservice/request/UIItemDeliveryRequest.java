package cn.edu.nju.software.common.pojo.bizservice.request;

import cn.edu.nju.software.common.pojo.EnvStatus;
import cn.edu.nju.software.common.pojo.ItemStatus;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/12 10:05
 */
@Data
public class UIItemDeliveryRequest {
    String itemId;
    EnvStatus envStatus;
    ItemStatus itemStatus;
    String nextOrg;
    String contact;
}
