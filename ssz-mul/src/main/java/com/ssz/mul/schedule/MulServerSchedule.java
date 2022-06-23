package com.ssz.mul.schedule;

import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.discovery.AbstractDiscoveryOwner;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class MulServerSchedule extends AbstractSecondSchedule{
    private AbstractDiscoveryOwner instanceOwner;

    private DiscoveryProperties discoveryProperties;

    private AtomicInteger atomic = new AtomicInteger(0);

    private final NacosDiscoveryScheduleManager manager;

    @Override
    public void schedule() {
        boolean hasIns = instanceOwner.refreshServiceInstances(getRegisterId(), getGroup(), getServiceId());
        if (hasIns) {
            atomic.set(0);
        } else {
            if (atomic.incrementAndGet() >= discoveryProperties.getPullEmptyCount()) {
                manager.delSecondSchedules(this);
            }
        }
    }

    public MulServerSchedule(String registerId, String groupName, String serviceId, ConfigurableListableBeanFactory beanFactory) {
        super(registerId, groupName, serviceId);
        this.discoveryProperties = beanFactory.getBean(DiscoveryProperties.class);
        this.instanceOwner = beanFactory.getBean(AbstractDiscoveryOwner.class);
        this.manager = beanFactory.getBean(NacosDiscoveryScheduleManager.class);
    }

    @Override
    protected Integer getPullInterval() {
        return discoveryProperties.getPullInterval();
    }
}
