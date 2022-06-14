package com.ssz.gateway.filter;

import com.ssz.common.web.constants.DiscoveryConstant;
import com.ssz.common.web.ribbon.HeaderThreadLocal;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@Data
@ConfigurationProperties(prefix = "system.config")
@RefreshScope
public class GatewayFilter implements GlobalFilter, Ordered {

    private List<String> ignoreTokenUrls;

    private final String token = "token";

    private HeaderThreadLocal headerThreadLocal;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get(token);
        //绑定请求头route的值
        List<String> routeList = headers.get(DiscoveryConstant.ROUTE_FIELD_NAME);
        if (!CollectionUtils.isEmpty(routeList)){
            headerThreadLocal.setLocal(routeList.get(0));
        }
        String token = tokens.get(0);
        String path = request.getURI().getPath();
        //不使用token
        if (ignoreTokenUrls.contains(path)) {
            return chain.filter(exchange);
        }
        if (!Objects.equals(token, "12345")) {
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //过滤器的顺序，0 表示第一个
        return 0;
    }
}
