package com.ssz.common.web.ribbon;

import com.netflix.ribbon.RequestTemplate;
import lombok.Data;

@Data
public class RequestTemplateWrap {
    private RequestTemplate requestTemplate;

    public RequestTemplateWrap(RequestTemplate requestTemplate) {
        this.requestTemplate = requestTemplate;
    }
}
