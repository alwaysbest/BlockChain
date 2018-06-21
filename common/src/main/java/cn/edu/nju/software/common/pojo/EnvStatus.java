package cn.edu.nju.software.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniel
 * @since 2018/4/28 16:44
 * 设备外部环境状态，包括地址信息（经度、纬度，必需有），通过支持的各类传感器可以扩展支持的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvStatus {
    AddressInfo addressInfo;
}
