package com.ssz.mul.config;
import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.discovery.AbstractDiscoveryOwner;
import com.ssz.mul.nacos.ServerDiscoveryOwner;
import com.ssz.mul.schedule.ExtendSecondScheduleRegister;
import com.ssz.mul.schedule.MulSecondScheduleRegister;
import com.ssz.mul.schedule.NacosDiscoveryScheduleManager;
import com.ssz.mul.schedule.RegistryBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@EnableConfigurationProperties(DiscoveryProperties.class)
@ConditionalOnProperty(prefix = MulConstant.DISCOVERY_PREFIX, value = MulConstant.ENABLED, matchIfMissing = true)
public class DiscoveryConfiguration {

    @Bean
    public RegistryBeanPostProcessor registryBeanPostProcessor() {
        return new RegistryBeanPostProcessor();
    }

    @Bean
    public NacosDiscoveryScheduleManager nacosDiscoveryScheduleManager(DiscoveryProperties grayProperties
            , @Lazy AbstractDiscoveryOwner instanceOwner
            , @Lazy ExtendSecondScheduleRegister register) {
        return new NacosDiscoveryScheduleManager(grayProperties, instanceOwner, register);
    }

    @Bean
    @ConditionalOnMissingBean(ExtendSecondScheduleRegister.class)
    public MulSecondScheduleRegister defaultSecondScheduleRegister(ConfigurableListableBeanFactory beanFactory) {
        return new MulSecondScheduleRegister(beanFactory);
    }

    @Bean
    @ConditionalOnMissingBean(AbstractDiscoveryOwner.class)
    public ServerDiscoveryOwner serverGrayInstanceOwner(DiscoveryProperties discoveryProperties,
                                                        NacosDiscoveryScheduleManager manager) {
        return new ServerDiscoveryOwner(discoveryProperties, manager);
    }

}
