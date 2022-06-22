package com.ssz.mul.config;

import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.element.ElementGroup;
import com.ssz.mul.element.ElementRegisterId;
import com.ssz.mul.element.ElementRoute;
import com.ssz.mul.loadbalancer.InsFilter;
import com.ssz.mul.loadbalancer.InstancePreprocessor;
import com.ssz.mul.ribbon.MulInstancePreprocessor;
import com.ssz.mul.ribbon.RouteRequestHeaderTransmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnBean(SpringClientFactory.class)
@ConditionalOnClass(RibbonClient.class)
@RibbonClients(defaultConfiguration = MulRibbonClientConfiguration.class)
@ConditionalOnProperty(prefix = MulConstant.DISCOVERY_PREFIX, value = MulConstant.ENABLED, matchIfMissing = true)
public class RibbonMulAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ElementGroup.class)
    public ElementGroup elementGroup() {
        return new ElementGroup();
    }

    @Bean
    @ConditionalOnMissingBean(ElementRegisterId.class)
    public ElementRegisterId elementRegisterId() {
        return new ElementRegisterId();
    }

    @Bean
    @ConditionalOnMissingBean(ElementRoute.class)
    public ElementRoute elementRoute() {
        return new ElementRoute();
    }

    @Bean
    @ConditionalOnMissingBean(RouteRequestHeaderTransmit.class)
    public RouteRequestHeaderTransmit routeRequestHeaderTransmit() {
        return new RouteRequestHeaderTransmit();
    }

    @Bean
    @ConditionalOnMissingBean(InstancePreprocessor.class)
    public MulInstancePreprocessor mulInsFilter(ElementRegisterId registerId,
                                                 ElementGroup group,
                                                 ElementRoute route,
                                                 LoadBalancerProperties loadBalancerProperties,
                                                 @Autowired(required = false) List<InsFilter> insFilters) {
        return new MulInstancePreprocessor(registerId, group, route, insFilters, loadBalancerProperties);
    }

}
