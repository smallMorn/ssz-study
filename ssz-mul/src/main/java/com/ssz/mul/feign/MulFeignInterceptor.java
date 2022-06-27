package com.ssz.mul.feign;

import com.ssz.mul.config.DiscoveryProperties;
import com.ssz.mul.ribbon.HeaderThreadLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.Map;

import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

public class MulFeignInterceptor implements RequestInterceptor {
    private final DiscoveryProperties discoveryProperties;

    private final HeaderThreadLocal headerThreadLocal;

    public MulFeignInterceptor(DiscoveryProperties discoveryProperties, HeaderThreadLocal headerThreadLocal) {
        this.discoveryProperties = discoveryProperties;
        this.headerThreadLocal = headerThreadLocal;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        headerThreadLocal.getLocal().clear();
        requestTemplate.header(discoveryProperties.getRouteFieldName(), getRouteValue(discoveryProperties.getRouteFieldName()));
        Map<String, Collection<String>> headers = requestTemplate.headers();
        headers.keySet().forEach(key -> {
            Collection<String> collection = headers.get(key);
            if (!CollectionUtils.isEmpty(collection)) {
                headerThreadLocal.setLocal(key, collection.toArray()[0]);
            }
        });
    }

    /**
     * @param routeName route标签的名称
     * @return 返回route标签的值 优先取请求头里面的 如果请求头里面没有就取环境变量
     */
    private String getRouteValue(String routeName) {
        String routeValue = null;
        HttpServletRequest request = getRequest();
        if (request != null) {
            String header = request.getHeader(routeName);
            if (!StringUtils.isEmpty(header)) {
                routeValue = header;
            }
        }
        if (routeValue == null) {
            routeValue = discoveryProperties.getRoute();
        }
        return routeValue;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

}
