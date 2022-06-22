package com.ssz.mul.config;

import com.ssz.mul.constants.MulConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(MulConstant.LOADBALANCER_PREFIX)
@Setter
@Getter
public class LoadBalancerProperties {

    /**
     * route服务不存在时是否降级访问其他服务（注：相同group、serviceId下的其他服务）
     */
    private boolean demotion = true;

    /**
     * 默认访问route
     */
    private String defaultAccessRoute = MulConstant.DEFAULT_ROUTE;

}
