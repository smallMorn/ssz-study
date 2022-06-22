package com.ssz.mul.schedule;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public final class RegistryBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static BeanDefinitionRegistry registry;

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RegistryBeanPostProcessor.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        RegistryBeanPostProcessor.beanFactory = beanFactory;
    }

    public static BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
