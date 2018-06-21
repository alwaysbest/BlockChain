package cn.edu.nju.software.common.pojo.fabricservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Daniel
 * @since 2018/4/28 15:33
 */
@NoArgsConstructor
@AllArgsConstructor
public class FSRequest {
    /**
     * 调用参数
     */
    @Getter
    @Setter
    private Map<String, String> para;
    /**
     * 是否为同步调用
     */
    @Getter
    @Setter
    private boolean isSync;
    /**
     * 回调函数
     */
    @Getter
    @Setter
    private Consumer<Object> callback;
    /**
     * 超时时间
     */
    @Getter
    @Setter
    private long timeout;
    /**
     * 重试次数
     */
    @Getter
    @Setter
    private int retryTimes;

}
