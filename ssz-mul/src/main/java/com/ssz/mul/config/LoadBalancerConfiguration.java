package com.ssz.mul.config;

import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.ribbon.HeaderThreadLocal;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoadBalancerProperties.class)
@ConditionalOnProperty(prefix = MulConstant.DISCOVERY_PREFIX, value = MulConstant.ENABLED, matchIfMissing = true)
public class LoadBalancerConfiguration {

    @Bean
    @ConditionalOnMissingBean(HeaderThreadLocal.class)
    public HeaderThreadLocal headerThreadLocal() {
        return new HeaderThreadLocal();
    }
}
