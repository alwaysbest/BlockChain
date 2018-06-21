package cn.edu.nju.software.fabricservice.servicediscovery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniel
 * @since 2018/5/7 20:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscoveryParas {
    LoadBalanceType loadBalanceType;
    Integer peerNum = 1;
}
