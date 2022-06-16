package com.ssz.mul.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.ssz.mul.constants.DiscoveryConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@ConfigurationProperties(DiscoveryConstant.DISCOVERY_PREFIX)
@Data
@Component
public class DiscoveryProperties {

    /**
     * route属性的值
     */
    public String route = "default";

    /**
     * route属性的名称
     */
    public String routeFieldName;

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @PostConstruct
    public void init() {
        this.overrideFromEnv();
    }

    private void overrideFromEnv() {
        if (StringUtils.isEmpty(getRouteFieldName())) {
            setRouteFieldName(DiscoveryConstant.ROUTE_FIELD_NAME);
        }
        String nacosRoute = nacosDiscoveryProperties.getMetadata().get(getRouteFieldName());
        if (!StringUtils.isEmpty(nacosRoute)) {
            setRoute(nacosRoute);
        } else {
            nacosDiscoveryProperties.getMetadata().put(getRouteFieldName(), getRoute());
        }
    }

}
