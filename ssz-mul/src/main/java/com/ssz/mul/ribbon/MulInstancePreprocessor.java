package com.ssz.mul.ribbon;

import com.netflix.loadbalancer.Server;
import com.ssz.mul.config.LoadBalancerProperties;
import com.ssz.mul.element.ElementGroup;
import com.ssz.mul.element.ElementRegisterId;
import com.ssz.mul.element.ElementRoute;
import com.ssz.mul.loadbalancer.InsFilter;
import com.ssz.mul.loadbalancer.InsTemplate;
import com.ssz.mul.loadbalancer.InstancePreprocessor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MulInstancePreprocessor implements InstancePreprocessor {

    private final ElementRegisterId registerId;

    private final ElementGroup group;

    private final ElementRoute route;

    private final List<InsFilter> insFilters;

    private final LoadBalancerProperties lbProperties;

    public MulInstancePreprocessor(ElementRegisterId registerId, ElementGroup group, ElementRoute route, List<InsFilter> insFilters, LoadBalancerProperties lbProperties) {

        this.registerId = registerId;
        this.group = group;
        this.route = route;
        this.lbProperties = lbProperties;
        if (CollectionUtils.isEmpty(insFilters)) {
            insFilters = new ArrayList<>();
        }
        this.insFilters = insFilters.stream().sorted(Comparator.comparing(InsFilter::order)).collect(Collectors.toList());
    }

    @Override
    public List<Server> process(Map<String, Object> map, InsTemplate<Server> cacheIns, String serviceId) {
        String routeTag = route.get(map);
        String rId = registerId.get(map);
        String g = group.get(map);
        List<Server> ins = cacheIns.getInstancesByRoute(rId, g, serviceId, routeTag);
        //route降级
        if (CollectionUtils.isEmpty(ins) && lbProperties.isDemotion() && !lbProperties.getDefaultAccessRoute().equals(routeTag)) {
            ins = cacheIns.getInstancesByRoute(rId, g, serviceId, lbProperties.getDefaultAccessRoute());
        }
        return ins;
    }

}
