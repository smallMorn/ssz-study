package com.ssz.mul.schedule;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.constants.MulConstant;
import com.ssz.mul.discovery.AbstractDiscoveryOwner;
import com.ssz.mul.nacos.NacosRegister;
import com.ssz.mul.utils.DiscoveryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NacosDiscoveryScheduleManager implements InitializingBean {



    private Map<String, NamingService> naming = new ConcurrentHashMap<>();

    private ConfigService configService;

    private Map<String, AbstractSecondSchedule> schedules = new ConcurrentHashMap<>(64);

    public NacosDiscoveryScheduleManager(DiscoveryProperties discoveryProperties, AbstractDiscoveryOwner instanceOwner, ExtendSecondScheduleRegister scheduleRegister) {
        this.discoveryProperties = discoveryProperties;
        this.instanceOwner = instanceOwner;
        this.scheduleRegister = scheduleRegister;
    }

    private final DiscoveryProperties discoveryProperties;

    private final AbstractDiscoveryOwner instanceOwner;

    private final ExtendSecondScheduleRegister scheduleRegister;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (NacosRegister register : discoveryProperties.getRegister()) {
            Properties properties = getProperties(register);
            naming.put(register.getRegisterId(), NacosFactory.createNamingService(properties));
        }
        NacosRegister register = new NacosRegister();
        NacosDiscoveryProperties nacosProperties = discoveryProperties.getNacosDiscoveryProperties();
        register.setRemoteAddress(nacosProperties.getServerAddr());
        register.setNamespace(nacosProperties.getNamespace());
        register.setUsername(nacosProperties.getUsername());
        register.setPassword(nacosProperties.getPassword());
        Properties properties = getProperties(register);
        configService = NacosFactory.createConfigService(properties);
    }

    private Properties getProperties(NacosRegister register) {
        Properties properties = new Properties();
        if (register.getRemoteAddress().startsWith("http")) {
            if (!StringUtils.isEmpty(register.getUsername())) {
                properties.put(PropertyKeyConst.USERNAME, register.getUsername());
            }
            if (!StringUtils.isEmpty(register.getPassword())) {
                properties.put(PropertyKeyConst.PASSWORD, register.getPassword());
            }
        }
        properties.put(PropertyKeyConst.SERVER_ADDR, register.getRemoteAddress());
        properties.put(PropertyKeyConst.NAMESPACE, register.getNamespace());
        return properties;
    }

    public NamingService getNaming() {
        return naming.get(MulConstant.DEFAULT_REGISTER_ID);
    }

    public ConfigService getConfigService() {
        return configService;
    }

    public NamingService getNaming(String registerId) {
        return naming.get(registerId);
    }

    public List<AbstractSecondSchedule> getSecondSchedules() {
        return new ArrayList<>(schedules.values());
    }

    public void putSecondSchedules(List<AbstractSecondSchedule> secondSchedules) {
        secondSchedules.forEach(this::putSecondSchedules);
    }

    public void putSecondSchedules(AbstractSecondSchedule secondSchedule) {
        final String key = DiscoveryUtils.getOwnerKey(secondSchedule.getRegisterId(), secondSchedule.getGroup(), secondSchedule.getServiceId());
        if (schedules.containsKey(key)) {
            delSecondSchedules(secondSchedule);
        }
        DiscoveryUtils.getScheduledExecutorService().schedule(secondSchedule, MulConstant.DEFAULT_PULL_INTERVAL, TimeUnit.SECONDS);
        schedules.put(key, secondSchedule);
    }

    public void delSecondSchedules(AbstractSecondSchedule secondSchedule) {
        final String key = DiscoveryUtils.getOwnerKey(secondSchedule.getRegisterId(), secondSchedule.getGroup(), secondSchedule.getServiceId());
        instanceOwner.getRegistered().remove(key);
        schedules.get(key).cease();
        String beanName = DiscoveryUtils.getBeanName(secondSchedule.getRegisterId(), secondSchedule.getGroup(), secondSchedule.getServiceId());
        RegistryBeanPostProcessor.getBeanFactory().destroyBean(beanName, secondSchedule);
        RegistryBeanPostProcessor.getRegistry().removeBeanDefinition(beanName);
        schedules.remove(secondSchedule.getGroup() + MulConstant.JOINT + secondSchedule.getServiceId());
    }

    public synchronized void putSecondSchedulesByGroup(String registerId, String group, String serviceId) {
        BeanDefinitionRegistry registry = RegistryBeanPostProcessor.getRegistry();
        AbstractBeanDefinition beanDefinition = scheduleRegister.getHolder(registerId, group, serviceId);
        String beanName = DiscoveryUtils.getBeanName(registerId, group, serviceId);
        if (!registry.isBeanNameInUse(beanName)) {
            BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, beanName);
            BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
            AbstractSecondSchedule schedule = (AbstractSecondSchedule) RegistryBeanPostProcessor
                    .getBeanFactory().getBean(beanName);
            putSecondSchedules(schedule);
            log.info("新增监听器，监听器group：{}，serviceId：{}", group, serviceId);
        }
    }
}
