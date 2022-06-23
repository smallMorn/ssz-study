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

    /**
     * 拉取实例空次数，阈值达到后自动关闭定时拉取任务
     */
    private Integer pullEmptyCount;

    /**
     * 实例拉取间隔 默认5s
     */
    private Integer pullInterval;

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

    /**
     * 服务所属的分组
     */
    public String getGroup() {
        return nacosDiscoveryProperties.getGroup();
    }

}
