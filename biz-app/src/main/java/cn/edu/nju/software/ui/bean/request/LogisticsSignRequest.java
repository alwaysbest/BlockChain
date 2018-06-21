package cn.edu.nju.software.ui.bean.request;

import cn.edu.nju.software.common.pojo.AddressInfo;
import lombok.Data;

import java.util.List;

/**
 * @author Daniel
 * @since 2018/5/13 23:25
 */
@Data
public class LogisticsSignRequest {
    String order;
    String name;
    String email;
    AddressInfo addressInfo;
}
