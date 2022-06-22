package com.ssz.mul.schedule;

import org.springframework.beans.factory.support.AbstractBeanDefinition;

public interface ExtendSecondScheduleRegister {
    /**
     * 获取BeanDefinition
     *
     * @param registerId 注册中心id
     * @param group      实例分组
     * @param serviceId  服务id
     * @return
     */
    AbstractBeanDefinition getHolder(String registerId, String group, String serviceId);

}
