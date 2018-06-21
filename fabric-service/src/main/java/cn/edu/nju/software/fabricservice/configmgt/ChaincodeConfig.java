package cn.edu.nju.software.fabricservice.configmgt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Daniel
 * @since 2018/5/7 19:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChaincodeConfig {
    String name;
    String version;
    String user;
    String channel;
    List<String> func;
    List<String> peers;
}
