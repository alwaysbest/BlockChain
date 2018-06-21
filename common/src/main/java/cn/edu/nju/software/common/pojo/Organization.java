package cn.edu.nju.software.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Daniel
 * @since 2018/4/28 16:25
 */
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    /**
     * 组织名字
     */
    @Setter
    @Getter
    private String name;
}
