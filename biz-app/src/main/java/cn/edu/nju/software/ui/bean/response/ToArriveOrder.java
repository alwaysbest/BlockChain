package cn.edu.nju.software.ui.bean.response;

import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/14 2:30
 */
@Data
public class ToArriveOrder {
    String orderDesc;
    String orderNum;
    String from;
    String fromTime;
}
