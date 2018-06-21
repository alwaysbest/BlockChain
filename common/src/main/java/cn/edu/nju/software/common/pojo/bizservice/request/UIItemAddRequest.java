package cn.edu.nju.software.common.pojo.bizservice.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import cn.edu.nju.software.common.pojo.ItemInfo;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/2 16:26
 */
@Data
public class UIItemAddRequest {
    String authCode;
    String itemId;
    ItemInfo itemInfo;
    AddressInfo addressInfo;
}
