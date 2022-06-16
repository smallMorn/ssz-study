package com.ssz.mul.filter;

import com.ssz.mul.ribbon.HeaderThreadLocal;
import com.ssz.mul.ribbon.ThreadLocalParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
public class MulLoadBalancerClientFilter implements GlobalFilter, Ordered {

    private final LoadBalancerProperties properties;

    private final LoadBalancerClient loadBalancer;

    protected final HeaderThreadLocal<ThreadLocalParam> headerThreadLocal;

    public MulLoadBalancerClientFilter(LoadBalancerProperties properties
            , LoadBalancerClient loadBalancer
            , HeaderThreadLocal<ThreadLocalParam> headerThreadLocal) {
        this.properties = properties;
        this.loadBalancer = loadBalancer;
        this.headerThreadLocal = headerThreadLocal;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
            return chain.filter(exchange);
        }
        // preserve the original url
        ServerWebExchangeUtils.addOriginalRequestUrl(exchange, url);
        exchange.getResponse();
        if (log.isTraceEnabled()) {
            log.trace("LoadBalancerClientFilter url before: " + url);
        }
        return this.choose(exchange).doOnNext((response) -> {
            if (!response.hasServer()) {
                throw NotFoundException.create(true, "Unable to find instance for " + url.getHost());
            } else {
                URI uri = exchange.getRequest().getURI();
                String overrideScheme = null;
                if (schemePrefix != null) {
                    overrideScheme = url.getScheme();
                }
                DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(response.getServer(), overrideScheme);
                URI requestUrl = LoadBalancerUriTools.reconstructURI(serviceInstance, uri);
                if (log.isTraceEnabled()) {
                    log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
                }
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
            }
        }).then(chain.filter(exchange));
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        return Mono.create(o -> o.success(response(exchange)));
    }

    private Response<ServiceInstance> response(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        Response<ServiceInstance> response;
        try {
            ThreadLocalParam localLocal = headerThreadLocal.getLocal();
            if (localLocal != null) {
                //快到原生gateway负载，故设为null，否则接下来的路由依旧会按feign的配置项处理
                localLocal.setRequestTemplateWrap(null);
            }
            ServiceInstance ins = loadBalancer.choose(uri.getHost());
            if (ins == null) {
                response = empty(uri.getHost());
            } else {
                response = new DefaultResponse(ins);
            }
        } finally {
            headerThreadLocal.removeLocal();
        }
        return response;
    }

    protected Response<ServiceInstance> empty(String serviceId) {
        log.warn("No servers available for serviceId: " + serviceId);
        return new EmptyResponse();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
