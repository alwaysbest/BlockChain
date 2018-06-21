package cn.edu.nju.software.ui.bean.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/14 15:49
 */
@Data
public class DealerAddReqeust {
    int itemTypeId;
    String items;
    AddressInfo addressInfo;
}
