package com.ssz.mul.ribbon;

import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.loadbalancer.RequestHeaderTransmit;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class RouteRequestHeaderTransmit implements RequestHeaderTransmit {
    @Resource
    private DiscoveryProperties discoveryProperties;

    @Override
    public List<String> transmit() {
        return Collections.singletonList(discoveryProperties.getRouteFieldName());
    }
}
