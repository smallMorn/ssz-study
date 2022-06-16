package com.ssz.mul.ribbon;

import lombok.Data;
import org.springframework.web.server.ServerWebExchange;

@Data
public class ThreadLocalParam {

    private ServerWebExchange exchange;

    /**
     * 满足gateway同时需要feign调度的需求
     */
    private RequestTemplateWrap requestTemplateWrap;

}
