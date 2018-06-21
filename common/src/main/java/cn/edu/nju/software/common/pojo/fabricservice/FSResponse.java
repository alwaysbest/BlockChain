package cn.edu.nju.software.common.pojo.fabricservice;

import cn.edu.nju.software.common.pojo.RespStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Daniel
 * @since 2018/4/28 15:39
 */
@NoArgsConstructor
@AllArgsConstructor
public class FSResponse<T> {
    @Getter
    @Setter
    public RespStatus respStatus;
    @Getter
    @Setter
    public T data;

    public static <T> FSResponse<T> create(T data, Integer code, String msg, String... args) {
        return new FSResponse<>(new RespStatus(code, String.format(msg, args)), data);
    }

    public static <T> FSResponse<T> createSuccess(T data, String msg, String... args) {
        return create(data, RespStatus.SUCCESS_CODE, String.format(msg, args));
    }

    public static <T> FSResponse<T> createWithoutData(Integer code, String msg, String... args) {
        return create(null, code, msg, args);
    }

}
