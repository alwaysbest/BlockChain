package cn.edu.nju.software.fabricservice.serviceinvoker;

import cn.edu.nju.software.fabricservice.bean.SampleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Daniel
 * @since 2018/5/7 19:15
 */
@AllArgsConstructor
@NoArgsConstructor
public class InvokeContext {
    @Getter
    @Setter
    SampleUser sampleUser;
    @Getter
    @Setter
    String channelName;
    @Setter
    @Getter
    List<String> peerNames;
}
