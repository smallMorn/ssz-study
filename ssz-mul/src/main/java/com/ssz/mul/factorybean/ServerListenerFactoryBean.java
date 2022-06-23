package com.ssz.mul.factorybean;

import com.ssz.mul.schedule.MulServerSchedule;
import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@Data
public class ServerListenerFactoryBean implements FactoryBean<MulServerSchedule> {

    private final String registerId;

    private final String groupName;

    private final String serviceId;

    private final ConfigurableListableBeanFactory beanFactory;

    public ServerListenerFactoryBean(String registerId, String groupName, String serviceId
            , ConfigurableListableBeanFactory beanFactory) {
        this.registerId = registerId;
        this.groupName = groupName;
        this.serviceId = serviceId;
        this.beanFactory = beanFactory;
    }

    @Override
    public MulServerSchedule getObject() {
        return new MulServerSchedule(registerId, groupName, serviceId, beanFactory);
    }

    @Override
    public Class<?> getObjectType() {
        return MulServerSchedule.class;
    }
}
