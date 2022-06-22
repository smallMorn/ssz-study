package com.ssz.mul.loadbalancer;

import com.netflix.loadbalancer.Server;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface InsFilter {

    /**
     * 过滤实例
     *
     * @param headers 请求头
     * @param ins     传递的实例
     * @return 过滤后你所需要的实例
     */
    List<Server> process(Map<String, Collection<String>> headers, List<Server> ins);

    /**
     * 顺序(越小优先执行)
     *
     * @return int
     */
    int order();
}
