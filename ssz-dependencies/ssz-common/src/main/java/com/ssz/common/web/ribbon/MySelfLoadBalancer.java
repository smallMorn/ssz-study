package com.ssz.common.web.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySelfLoadBalancer implements ILoadBalancer {

    private final IClientConfig iClientConfig;

    private final IRule rule;

    private final HeaderThreadLocal headerThreadLocal;


    public MySelfLoadBalancer(IClientConfig iClientConfig, IRule rule, HeaderThreadLocal headerThreadLocal) {
        this.iClientConfig = iClientConfig;
        this.rule = rule;
        this.headerThreadLocal = headerThreadLocal;
    }

    @Override
    public void addServers(List<Server> list) {

    }

    @Override
    public Server chooseServer(Object key) {
        return rule.choose(key);
    }

    @Override
    public void markServerDown(Server server) {

    }

    @Override
    public List<Server> getServerList(boolean b) {
        return null;
    }

    @Override
    public List<Server> getReachableServers() {
        Map<String, Collection<String>> headers =  headerThreadLocal.getHeaders();
        if (headers == null) {
            headers = new HashMap<>(1);
        }
        return null;
    }

    @Override
    public List<Server> getAllServers() {
        return getReachableServers();
    }

}
