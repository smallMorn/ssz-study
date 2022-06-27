package com.ssz.mul.element;

import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.constants.MulConstant;
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
    public String get(Map<String, Object> map) {
        return gain(map, MulConstant.ROUTE_KEY, discoveryProperties.getRoute());
    }

}
