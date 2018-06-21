package cn.edu.nju.software.fabricservice.configmgt;

import cn.edu.nju.software.common.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @author Daniel
 * @since 2018/5/7 18:42
 * 配置中心
 */
public class ConfigMgt {
    Logger logger = LoggerFactory.getLogger(ConfigMgt.class);
    HFConfig hfConfig;

    public void init(ConfigType configType, Object... paras) {
        switch (configType) {
            case FILE:
                if (paras == null || paras.length == 0) {
                    hfConfig = HFConfig.newInstance();
                } else {
                    if (!(paras[0] instanceof String)) {
                        LoggerUtil.errorf(logger, "excepted String input,actual:", paras[0]
                                .getClass().getName());
                    }
                    try {
                        hfConfig = HFConfig.newInstanceByFile((String) paras[0]);
                    } catch (IOException e) {
                        LoggerUtil.errorf(logger, "init hfConfig error, can not read file:%s",
                                (String) paras[0]);
                        e.printStackTrace();
                    }
                }
                break;
            case CONFIG_CENTER:
                break;
        }
    }

    public HFConfig getConfig() {
        if (hfConfig == null)
            return null;
        return hfConfig;
    }
}
