package com.ssz.mul.schedule;

import com.ssz.mul.factorybean.ServerListenerFactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class MulSecondScheduleRegister implements ExtendSecondScheduleRegister{

    private final ConfigurableListableBeanFactory beanFactory;

    public MulSecondScheduleRegister(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    @Override
    public AbstractBeanDefinition getHolder(String registerId, String group, String serviceId) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .genericBeanDefinition(ServerListenerFactoryBean.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, registerId);
        argumentValues.addIndexedArgumentValue(1, group);
        argumentValues.addIndexedArgumentValue(2, serviceId);
        argumentValues.addIndexedArgumentValue(3, beanFactory);
        beanDefinition.setConstructorArgumentValues(argumentValues);
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        return beanDefinition;
    }
}
