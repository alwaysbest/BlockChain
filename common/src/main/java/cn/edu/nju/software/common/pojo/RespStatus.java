package cn.edu.nju.software.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 响应状态
 */
@AllArgsConstructor
@NoArgsConstructor
public class RespStatus {
    public static final Integer SUCCESS_CODE = 0;
    /**
     * 状态码
     */
    @Getter
    @Setter
    private Integer code;
    /**
     * 状态描述
     */
    @Getter
    @Setter
    private String msg;

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }

}
