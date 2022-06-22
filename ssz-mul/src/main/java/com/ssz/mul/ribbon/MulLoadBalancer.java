package com.ssz.mul.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.ssz.mul.loadbalancer.InsTemplate;
import com.ssz.mul.loadbalancer.InstancePreprocessor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MulLoadBalancer implements ILoadBalancer {
    private final InsTemplate<Server> instanceOwner;

    private final IClientConfig iClientConfig;

    private final IRule rule;

    private final HeaderThreadLocal headerThreadLocal;

    private final InstancePreprocessor instancePreprocessor;



    public MulLoadBalancer(IClientConfig iClientConfig, IRule rule, HeaderThreadLocal headerThreadLocal, InstancePreprocessor instancePreprocessor, InsTemplate<Server> instanceOwner) {
        this.iClientConfig = iClientConfig;
        this.rule = rule;
        this.headerThreadLocal = headerThreadLocal;
        this.instancePreprocessor = instancePreprocessor;
        this.instanceOwner = instanceOwner;
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
        return instancePreprocessor.process(headers, instanceOwner, iClientConfig.getClientName());
    }

    @Override
    public List<Server> getAllServers() {
        return getReachableServers();
    }

}
