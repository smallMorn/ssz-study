package com.ssz.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.ssz.common.web.enumerate.ApiCode;
import com.ssz.common.web.result.ResultInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        //不使用token
        if (ignoreTokenUrls.contains(path)) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get(token);
        if (!CollectionUtils.isEmpty(tokens)) {
            String token = tokens.get(0);
            if (Objects.equals(token, "12345")) {
                return chain.filter(exchange);
            }
        }
        return authError(response, ApiCode.TOKEN_ERROR);
    }

    private Mono<Void> authError(ServerHttpResponse resp, ApiCode apiCode) {
        resp.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        DataBuffer buffer = resp.bufferFactory().wrap(JSONObject.toJSONString(ResultInfo.fail(apiCode)).getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        //过滤器的顺序，0 表示第一个
        return 0;
    }
}
