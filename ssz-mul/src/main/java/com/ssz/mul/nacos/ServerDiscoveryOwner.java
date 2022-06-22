package com.ssz.mul.nacos;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.exception.runtime.NacosRuntimeException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Lists;
import com.netflix.loadbalancer.Server;
import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.discovery.AbstractDiscoveryOwner;
import com.ssz.mul.schedule.NacosDiscoveryScheduleManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ServerDiscoveryOwner extends AbstractDiscoveryOwner<Server> {

    public ServerDiscoveryOwner(DiscoveryProperties discoveryProperties, NacosDiscoveryScheduleManager manager) {
        super(discoveryProperties, manager);
    }

    @Override
    public List<RouteInstance<Server>> getRouteInstances(String registerId, String group, String serviceId) {
        List<Instance> instances;
        try {
            instances = getManager().getNaming(registerId).selectInstances(serviceId, group, null, true, false);
        } catch (NacosException e) {
            e.printStackTrace();
            throw new NacosRuntimeException(500, "（断点中遇见可忽略，原因为请求超时机制判断）Nacos实例获取异常" + e.getErrMsg());
        }
        if (CollectionUtils.isEmpty(instances)) {
            return Lists.newLinkedList();
        }
        List<RouteInstance<Server>> instanceList = new ArrayList<>(instances.size());
        instances.forEach(instance -> {
            RouteInstance<Server> server = new RouteInstance<>();
            NacosServer nacosServer = new NacosServer(instance);
            nacosServer.setAlive(instance.isHealthy());
            server.setInstance(nacosServer);
            String route = instance.getMetadata().get(getDiscoveryProperties().getRouteFieldName());
            server.setRouteKey(StringUtils.isEmpty(route) ? MulConstant.DEFAULT_ROUTE : route);
            instanceList.add(server);
        });
        return instanceList;
    }
}
