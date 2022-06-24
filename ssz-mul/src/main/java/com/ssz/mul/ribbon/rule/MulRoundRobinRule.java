package com.ssz.mul.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.ssz.mul.utils.DiscoveryUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MulRoundRobinRule extends AbstractLoadBalancerRule {

    private final AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

    private ILoadBalancer lb;

    public MulRoundRobinRule(ILoadBalancer lb) {
        this.lb = lb;
        init();
    }

    public MulRoundRobinRule() {
        init();
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(lb, key);
    }

    private Server choose(ILoadBalancer lb, Object key) {
        List<Server> reachableServers = lb.getReachableServers();
        int upCount = reachableServers.size();
        if (upCount == 0) {
            log.warn("No up servers available from load balancer: " + lb);
            return null;
        }
        int nextServerIndex = incrementAndGetModulo(upCount);
        return reachableServers.get(nextServerIndex);
    }

    private int incrementAndGetModulo(int modulo) {
        for (; ; ) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next)) {
                return next;
            }
        }
    }

    private void init() {
        new CounterClearTask(DiscoveryUtils.getScheduledExecutorService(), 1).run();
    }

    private class CounterClearTask implements Runnable {

        private final ScheduledExecutorService executor;

        private final int time;

        CounterClearTask(ScheduledExecutorService executor, int time) {
            this.executor = executor;
            this.time = time;
        }

        @Override
        public void run() {
            nextServerCyclicCounter.set(0);
            executor.schedule(this, time, TimeUnit.DAYS);
        }
    }

}
