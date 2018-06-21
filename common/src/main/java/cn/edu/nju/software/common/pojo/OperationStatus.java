package cn.edu.nju.software.common.pojo;

import lombok.Data;

/**
 * @author Daniel
 * @since 2018/4/28 16:44
 * 操作状态，记录更改商品状态最后一次操作的相关信息
 */
@Data
public class OperationStatus {
    String organization;
    String lastOrganization;
    OpType opType;
    String contactWay;
    String extraInfo;
}
