package cn.edu.nju.software.common.pojo.bizservice.response;

import cn.edu.nju.software.common.pojo.RespStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 响应
 *
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
public class BizResponse<T> {
    /**
     * 响应状态码
     */
    @Setter
    @Getter
    public RespStatus respStatus;
    /**
     * 响应数据
     */
    @Setter
    @Getter
    public T respData;

    public static <T> BizResponse<T> create(T data, Integer code, String msg, String... args) {
        return new BizResponse<>(new RespStatus(code, String.format(msg, args)), data);
    }

    public static <T> BizResponse<T> createSuccess(T data, String msg, String... args) {
        return create(data, RespStatus.SUCCESS_CODE, String.format(msg, args));
    }

    public static <T> BizResponse<T> createWithoutData(Integer code, String msg, String... args) {
        return create(null, code, msg, args);
    }
    
    public static <T> BizResponse<T> defaultResponse(T data) {
        return create(data , RespStatus.SUCCESS_CODE , "success");
    }
    
}
