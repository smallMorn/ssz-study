package com.ssz.mul.loadbalancer;

import java.util.List;

public interface RequestHeaderTransmit {
    /**
     * 将需要透传的请求头放在list中
     * @return
     */
    List<String> transmit();

}
