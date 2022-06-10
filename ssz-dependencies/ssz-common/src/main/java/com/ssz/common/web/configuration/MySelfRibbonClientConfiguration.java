package com.ssz.common.web.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.ssz.common.web.ribbon.HeaderThreadLocal;
import com.ssz.common.web.ribbon.MySelfLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MySelfRibbonClientConfiguration {

    @RibbonClientName
    private String name = "client";

    @Autowired
    private PropertiesFactory propertiesFactory;

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer mySelfLoadBalancer(IClientConfig config, IRule rule, HeaderThreadLocal headerThreadLocal) {
        if (this.propertiesFactory.isSet(ILoadBalancer.class, name)) {
            return this.propertiesFactory.get(ILoadBalancer.class, config, name);
        }
        return new MySelfLoadBalancer(config, rule, headerThreadLocal);
    }

}
