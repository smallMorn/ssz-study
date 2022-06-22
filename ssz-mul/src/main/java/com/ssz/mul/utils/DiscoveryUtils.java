package com.ssz.mul.utils;

import com.alibaba.nacos.common.executor.NameThreadFactory;
import com.ssz.mul.constants.MulConstant;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class DiscoveryUtils {
    private static final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new NameThreadFactory("get nacos instance"));

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public static String getBeanName(String registerId, String group, String serviceId) {
        return MulConstant.DISCOVERY_PREFIX + registerId + MulConstant.JOINT + group + MulConstant.JOINT + serviceId + MulConstant.JOINT;
    }

    public static String getOwnerRouteKey(String registerId, String group, String serviceId, String route) {
        return getOwnerRouteKey(getOwnerKey(registerId, group, serviceId), route);
    }

    public static String getOwnerRouteKey(String ownerKey, String route) {
        return ownerKey + route + MulConstant.JOINT;
    }

    public static String getOwnerKey(String registerId, String group, String serviceId) {
        return registerId + MulConstant.JOINT + group + MulConstant.JOINT + serviceId + MulConstant.JOINT;
    }

}
