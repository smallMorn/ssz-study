package com.ssz.mul.config;
import com.ssz.mul.constants.MulConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DiscoveryProperties.class)
@ConditionalOnProperty(prefix = MulConstant.DISCOVERY_PREFIX, value = MulConstant.ENABLED, matchIfMissing = true)
public class DiscoveryConfiguration {

}
