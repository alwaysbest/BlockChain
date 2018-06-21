package cn.edu.nju.software.fabricservice.serviceinvoker;

import cn.edu.nju.software.fabricservice.configmgt.ConfigType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniel
 * @since 2018/5/7 18:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvokerInitParameters {
    /**
     * 配置文件类型
     */
    ConfigType configType;

    /**
     * 配置参数
     */
    Object[] configParas;

    public static Builder newBuilder() {
        return new Builder();
    }

    static class Builder {
        ConfigType configType;
        Object[] configParas;

        public Builder setConfigType(ConfigType configType) {
            this.configType = configType;
            return this;
        }

        public Builder setConfigParas(Object... configParas) {
            this.configParas = configParas;
            return this;
        }

        public InvokerInitParameters build() {
            return new InvokerInitParameters(configType, configParas);
        }
    }
}
