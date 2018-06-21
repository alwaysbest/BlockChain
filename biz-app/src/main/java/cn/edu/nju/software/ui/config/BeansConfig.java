package cn.edu.nju.software.ui.config;

import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Daniel
 * @since 2018/5/2 14:14
 */
@Configuration
public class BeansConfig {

    @Bean
    public ServiceInvoker getChaincodeInvoker() {
        return new ServiceInvoker();
    }

}
