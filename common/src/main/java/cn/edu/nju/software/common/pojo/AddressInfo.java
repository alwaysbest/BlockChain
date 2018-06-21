package cn.edu.nju.software.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Daniel
 * @since 2018/4/28 15:42
 */
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfo {
    /**
     * 经度
     */
    @Getter
    @Setter
    private double longtitude;
    /**
     * 纬度
     */
    @Getter
    @Setter
    private double latitude;
    /**
     * 地址描述
     */
    @Getter
    @Setter
    private String addressDesc;
    /**
     * 节点名字
     */
    @Getter
    @Setter
    private String nodeName;
}
