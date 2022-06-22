package com.ssz.mul.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.ssz.mul.loadbalancer.InsTemplate;
import com.ssz.mul.loadbalancer.InstancePreprocessor;
import com.ssz.mul.ribbon.HeaderThreadLocal;
import com.ssz.mul.ribbon.MulLoadBalancer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration(proxyBeanMethods = false)
public class MulRibbonClientConfiguration {

    @RibbonClientName
    private String name = "client";

    @Resource
    private PropertiesFactory propertiesFactory;

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer mulLoadBalancer(IClientConfig config, IRule rule,
                                         InsTemplate<Server> template,
                                         InstancePreprocessor preprocessor,
                                         HeaderThreadLocal headerThreadLocal) {

        if (this.propertiesFactory.isSet(ILoadBalancer.class, name)) {
            return this.propertiesFactory.get(ILoadBalancer.class, config, name);
        }
        return new MulLoadBalancer(config, rule, headerThreadLocal, preprocessor, template);
    }

}
