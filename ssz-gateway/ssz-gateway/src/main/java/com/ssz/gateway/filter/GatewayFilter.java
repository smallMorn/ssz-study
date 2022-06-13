package com.ssz.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.ssz.common.web.enumerate.ApiCode;
import com.ssz.common.web.result.ResultInfo;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@Data
@ConfigurationProperties(prefix = "system.config")
@RefreshScope
public class GatewayFilter implements GlobalFilter {

    private List<String> ignoreTokenUrls;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get("token");
        String token = tokens.get(0);
        String path = request.getURI().getPath();
        //不使用token
        if(ignoreTokenUrls.contains(path)){
            return chain.filter(exchange);
        }
        //正常解析token
        //return decrypt(path, token, exchange, chain, response);
        if (Objects.equals(token,"12345")){
            return chain.filter(exchange);
        }
        return authError(response);
    }

    /**
     * 验证失败直接响应异常
     *
     * @param resp 响应主体
     * @return 输出返回
     */
    private Mono<Void> authError(ServerHttpResponse resp) {
        resp.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        DataBuffer buffer = resp.bufferFactory().wrap(JSONObject.toJSONString(ResultInfo.fail(ApiCode.TOKEN_ERROR)).getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }
//
//    private Mono<Void> decrypt(String requestUri, String accessToken, ServerWebExchange exchange, GatewayFilterChain chain, ServerHttpResponse response) {
//        String requestPrefix = "/" + StringUtils.substringBefore(StringUtils.substringAfter(requestUri, "/"), "/");
//        Claims claims;
//        boolean flag = false;
//        boolean isFromMini = false;
//        try {
//            switch (requestPrefix) {
//                case JwtConstant.BUSINESS_REQUEST_PREFIX:
//                    flag = true;
//                    // 运营&商户后台请求
//                    claims = JwtUtils.parseJWT(accessToken, JwtConstant.BUSINESS_TOKEN_SERCRET);
//                    break;
//                default:
//                    log.error("请求路径 {} 不存在", requestUri);
//                    return authError(exchange.getResponse(), new BaseException(new ExceptionCode(404, "Not Found Url")));
//            }
//        } catch (Exception e) {
//            return authError(exchange.getResponse(), new BaseException(ExceptionCode.LOGIN_FAILURE));
//        }
//
//        String sellerId = claims.getSubject();
//        String userId = claims.getId();
//
//        //封装用户id与商户id
//        exchange.getRequest().mutate().headers(httpHeader -> {
//            httpHeader.set(CommonConstants.CONTEXT_KEY_USER_ID, userId);
//            httpHeader.set(CommonConstants.CONTEXT_SELLER_ID, sellerId);
//            httpHeader.set(GatewayGrayConstant.USER_ID, claims.getId());
//        }).build();
//        return chain.filter(exchange);
//    }

//    @Override
//    public int getOrder() {
//       // return GatewayOrdersConstants.LOAD_BALANCER_CLIENT_FILTER_ORDER-1;
//    }
}
