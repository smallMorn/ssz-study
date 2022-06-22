package com.ssz.mul.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.nacos.NacosRegister;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(MulConstant.DISCOVERY_PREFIX)
@Data
public class DiscoveryProperties {

    /**
     * route属性的值
     */
    public String route = "default";

    /**
     * route属性的名称
     */
    public String routeFieldName;
    /**
     * 需要监听的注册中心
     */
    private List<NacosRegister> register = new ArrayList<>();

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @PostConstruct
    public void init() {
        this.overrideFromEnv();
    }

    private void overrideFromEnv() {
        if (StringUtils.isEmpty(getRouteFieldName())) {
            setRouteFieldName(MulConstant.ROUTE_FIELD_NAME);
        }
        String nacosRoute = nacosDiscoveryProperties.getMetadata().get(getRouteFieldName());
        if (!StringUtils.isEmpty(nacosRoute)) {
            setRoute(nacosRoute);
        } else {
            nacosDiscoveryProperties.getMetadata().put(getRouteFieldName(), getRoute());
        }
    }

    public String getGroup() {
        return nacosDiscoveryProperties.getGroup();
    }

}
