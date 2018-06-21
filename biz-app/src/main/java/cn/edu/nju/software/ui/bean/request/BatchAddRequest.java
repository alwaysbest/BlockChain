package cn.edu.nju.software.ui.bean.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/13 23:19
 */
@Data
public class BatchAddRequest {
    String batchNum;
    int itemTypeId;
    String items;
    AddressInfo addressInfo;
}
