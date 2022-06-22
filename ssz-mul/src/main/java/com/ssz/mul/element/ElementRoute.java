package com.ssz.mul.element;

import com.ssz.mul.config.DiscoveryProperties;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

@Setter
@Getter
public class ElementRoute implements Element{

    @Resource
    private DiscoveryProperties discoveryProperties;

    @Override
    public String value(Map<String, Collection<String>> headers) {
        return discoveryProperties.getRoute();
    }

}
