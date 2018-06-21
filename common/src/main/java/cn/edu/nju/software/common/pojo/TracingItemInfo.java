package cn.edu.nju.software.common.pojo;

import lombok.Data;

/**
 * @author Daniel
 * @since 2018/4/28 15:40
 */
@Data
public class TracingItemInfo {
    /**
     * 时间戳
     */
    String time;
    String itemId;
    /**
     * 商品信息
     */
    ItemInfo itemInfo;
    /**
     * 商品状态
     */
    ItemStatus itemState;
    /**
     * 环境状态
     */
    EnvStatus envStatus;
    /**
     * 操作状态
     */
    OperationStatus operationStatus;

}
