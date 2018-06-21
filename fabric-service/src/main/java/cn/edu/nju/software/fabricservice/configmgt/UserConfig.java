package cn.edu.nju.software.fabricservice.configmgt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Daniel
 * @since 2018/5/1 0:24
 */
@AllArgsConstructor
@NoArgsConstructor
public class UserConfig {
    @Getter
    @Setter
    String username;
    @Getter
    @Setter
    String password;
    @Getter
    @Setter
    String org;
    @Getter
    @Setter
    String mspId;
}
