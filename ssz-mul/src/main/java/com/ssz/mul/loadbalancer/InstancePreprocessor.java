package com.ssz.mul.loadbalancer;

import com.netflix.loadbalancer.Server;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface InstancePreprocessor {

    /**
     * 固定处理流程
     *
     * @param headers   请求头
     * @param cacheIns  缓存中的所有实例
     * @param serviceId 请求serviceId
     * @return 选定你所需要的实例集合
     */
    List<Server> process(Map<String, Object> headers, InsTemplate<Server> cacheIns, String serviceId);
}
