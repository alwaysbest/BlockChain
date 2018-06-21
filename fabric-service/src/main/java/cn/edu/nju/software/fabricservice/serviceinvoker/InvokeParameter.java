package cn.edu.nju.software.fabricservice.serviceinvoker;

/**
 * @author Daniel
 * @since 2018/5/2 12:19
 */

import cn.edu.nju.software.fabricservice.bean.SampleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyperledger.fabric.sdk.BlockEvent;

import java.util.function.Consumer;

/**
 * 调用参数，用来控制调用过程，如是否异步等
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvokeParameter {
    private SampleUser invokeUser;
    /**
     * 返回类型
     */
    private ReturnType returnType;
    /**
     * 事件回调
     */
    private Consumer<BlockEvent.TransactionEvent> invokeCallBack;

}
