package com.ssz.mul.discovery;

import com.alibaba.nacos.common.utils.ConcurrentHashSet;
import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.loadbalancer.InsTemplate;
import com.ssz.mul.nacos.NacosRegister;
import com.ssz.mul.nacos.RouteInstance;
import com.ssz.mul.schedule.NacosDiscoveryScheduleManager;
import com.ssz.mul.utils.DiscoveryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractDiscoveryOwner<T> implements InitializingBean, InsTemplate {

    /**
     * 注：拼接key比嵌套map性能要更快，不区别route
     * <p>
     * map<key：registerId + && + group + && + serviceId  value：服务实例集合>
     * grou：分组  serviceId：服务名
     *
     * @see DiscoveryUtils#getOwnerKey(String, String, String) 使用此API来组装key
     */
    protected final Map<String, List<T>> allInstances = new ConcurrentHashMap<>(32);

    /**
     * 注：拼接key比嵌套map性能要更快
     * <p>
     * map<key：registerId + && + group + && + serviceId  + route  value：服务实例集合>
     * grou：分组  serviceId：服务名  route：（路由标签）
     *
     */
    protected final Map<String, List<T>> routeInstances = new ConcurrentHashMap<>(64);

    /**
     * 已注册定时任务的监听列表
     * Set<registerId + && + group + && + serviceId>
     */
    protected final Set<String> registered = new ConcurrentHashSet<>();

    /**
     * 监听中的注册中心
     */
    private final Map<String, NacosRegister> registerMap = new ConcurrentHashMap<>(8);

    protected final DiscoveryProperties discoveryProperties;

    protected final NacosDiscoveryScheduleManager manager;

    public AbstractDiscoveryOwner(DiscoveryProperties discoveryProperties, NacosDiscoveryScheduleManager manager) {
        this.discoveryProperties = discoveryProperties;
        this.manager = manager;
    }

    protected NacosDiscoveryScheduleManager getManager() {
        return manager;
    }

    protected DiscoveryProperties getDiscoveryProperties() {
        return discoveryProperties;
    }

    @Override
    public void afterPropertiesSet() {
        initializeProperty();
    }

    private void initializeProperty() {
        for (NacosRegister register : discoveryProperties.getRegister()) {
            registerMap.put(register.getRegisterId(), register);
        }
    }

    /**
     * 根据分组 + 服务名 + route 查找服务实例集合
     *
     * @param serviceId 服务id
     * @param group     分组
     * @param route     路由标签
     * @return 对应实例集合
     */
    @Override
    public List<T> getInstancesByRoute(String registerId, String group, String serviceId, String route) {
        String ownerKey = DiscoveryUtils.getOwnerKey(registerId, group, serviceId);
        if (!initializedSchedule(ownerKey)) {
            serviceInstancesRegistration(registerId, group, serviceId);
        }
        List<T> ins = routeInstances.get(DiscoveryUtils.getOwnerRouteKey(ownerKey, route));
        return Collections.unmodifiableList(Optional.ofNullable(ins).orElse(new LinkedList<>()));
    }

    @Override
    public Set<String> getRegistered() {
        return registered;
    }

    /**
     * 根据服务名 + route 查找服务实例集合
     *
     * @param serviceId 服务id
     * @param route     路由标签
     * @return 对应实例集合
     */
    @Override
    public List<T> getInstancesByRoute(String serviceId, String route) {
        return getInstancesByRoute(MulConstant.DEFAULT_REGISTER_ID, discoveryProperties.getGroup(), serviceId, route);
    }


    /**
     * 根据分组+服务名查找服务实例集合（如果你没有设置过route相关元素，那么此api就是你想要的）
     *
     * @param serviceId 服务id
     * @param group     分组
     * @return 对应实例集合
     */
    @Override
    public List<T> getInstances(String registerId, String group, String serviceId) {
        String ownerKey = DiscoveryUtils.getOwnerKey(registerId, group, serviceId);
        if (!initializedSchedule(ownerKey)) {
            serviceInstancesRegistration(registerId, group, serviceId);
        }
        List<T> ins = allInstances.get(ownerKey);
        return Collections.unmodifiableList(Optional.ofNullable(ins).orElse(new LinkedList<>()));
    }

    /**
     * 根据服务id拉取实例
     *
     * @param serviceId 服务id
     * @return 对应实例集合
     */
    @Override
    public List<T> getInstances(String serviceId) {
        return getInstances(MulConstant.DEFAULT_REGISTER_ID, discoveryProperties.getGroup(), serviceId);
    }


    /**
     * 初始化 该id下所有实例（所有监听的分组）
     *
     * @param serviceId 服务名id
     */
    protected void serviceInstancesRegistration(String registerId, String group, String serviceId) {
        NacosRegister nacosRegister = registerMap.get(registerId);
        Assert.notNull(nacosRegister, MulConstant.DISCOVERY_PREFIX + ".register未配置registerId：" + registerId);
        if (refreshServiceInstances(registerId, group, serviceId)) {
            registered.add(DiscoveryUtils.getOwnerKey(registerId, group, serviceId));
            manager.putSecondSchedulesByGroup(registerId, group, serviceId);
        }
    }

    /**
     * 是否初始化过定时器
     *
     * @param ownerKey registerId + && + group + && + serviceId
     */
    protected boolean initializedSchedule(String ownerKey) {
        return registered.contains(ownerKey);
    }

    /**
     * 刷新该分组下的服务id，如果已经有实例列表，将会被最新的覆盖
     *
     * @param registerId 注册中心id
     * @param group      分组
     * @param serviceId  服务id
     */
    public synchronized boolean refreshServiceInstances(String registerId, String group, String serviceId) {
        List<RouteInstance<T>> instances = getRouteInstances(registerId, group, serviceId);
        if (CollectionUtils.isEmpty(instances)) {
            insDel(registerId, group, serviceId);
            return false;
        }
        insAdd(registerId, group, serviceId, instances);
        return true;
    }

    protected void insAdd(String registerId, String group, String serviceId, List<RouteInstance<T>> instances) {
        List<T> rInstances = instances.stream().map(RouteInstance::getInstance).collect(Collectors.toList());
        String serviceKey = DiscoveryUtils.getOwnerKey(registerId, group, serviceId);
        allInstances.put(serviceKey, rInstances);
        Set<String> survivalRoutes = instances.stream().map(RouteInstance::getRouteKey).collect(Collectors.toSet());
        Set<String> cacheKeys = routeInstances.keySet().stream()
                .filter(key -> key.contains(DiscoveryUtils.getOwnerKey(registerId, group, serviceId)))
                .collect(Collectors.toSet());
        //当前缓存中存在的，如果在survivalRoutes已经没有的cacheKey，则删掉
        survivalRoutes.forEach(key -> {
            List<T> collect = instances.stream()
                    .filter(i -> key.equals(i.getRouteKey()))
                    .map(RouteInstance::getInstance)
                    .collect(Collectors.toList());
            String survivalKey = DiscoveryUtils.getOwnerRouteKey(registerId, group, serviceId, key);
            routeInstances.put(survivalKey, collect);
            cacheKeys.remove(survivalKey);
        });
        //删掉没有存活的route key
        cacheKeys.forEach(routeInstances::remove);
    }

    protected void insDel(String registerId, String group, String serviceId) {
        log.warn("实例拉取为空，registerId：{},serviceId：{}，group：{}", registerId, serviceId, group);
        allInstances.remove(DiscoveryUtils.getOwnerKey(registerId, group, serviceId));
        List<String> removeKeys = routeInstances.keySet().stream()
                .filter(key -> key.contains(DiscoveryUtils.getOwnerKey(registerId, group, serviceId)))
                .collect(Collectors.toList());
        removeKeys.forEach(routeInstances::remove);
    }

    /**
     * 返回拉取到的实例
     *
     * @param registerId 注册中心id
     * @param group      分组（Nacos的概念）
     * @param serviceId  服务id
     * @return RouteInstance
     */
    public abstract List<RouteInstance<T>> getRouteInstances(String registerId, String group, String serviceId);


}
