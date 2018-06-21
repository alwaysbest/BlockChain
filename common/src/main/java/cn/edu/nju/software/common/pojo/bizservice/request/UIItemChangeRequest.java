package cn.edu.nju.software.common.pojo.bizservice.request;

import cn.edu.nju.software.common.pojo.EnvStatus;
import cn.edu.nju.software.common.pojo.ItemStatus;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/2 19:34
 */
@Data
public class UIItemChangeRequest {
    String authCode;
    String itemId;
    EnvStatus envStatus;
    ItemStatus itemStatus;
    Integer opType;
    String contact;
    String nextOrg;
    String extraInfo;
}
