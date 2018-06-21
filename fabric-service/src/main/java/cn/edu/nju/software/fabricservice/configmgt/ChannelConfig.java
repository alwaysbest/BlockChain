package cn.edu.nju.software.fabricservice.configmgt;

import lombok.*;

import java.util.Map;

/**
 * @author Daniel
 * @since 2018/5/1 0:05
 */
@AllArgsConstructor
@NoArgsConstructor
public class ChannelConfig {
    @Setter
    @Getter
    private String channelName;
    @Setter
    @Getter
    private Map<String, String> peers;
    @Setter
    @Getter
    private Map<String, String> orderers;
    @Setter
    @Getter
    private Map<String, String> eventhubs;
}
