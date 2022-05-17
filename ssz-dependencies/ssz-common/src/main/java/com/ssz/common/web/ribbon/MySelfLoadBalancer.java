package com.ssz.common.web.ribbon;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MySelfLoadBalancer implements ILoadBalancer {

    private final IRule rule;

    public MySelfLoadBalancer(IRule rule) {
        this.rule = rule;
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
        return null;
    }

    @Override
    public List<Server> getAllServers() {
        return null;
    }
}
