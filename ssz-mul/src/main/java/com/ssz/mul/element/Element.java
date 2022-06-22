package com.ssz.mul.element;

import java.util.Collection;
import java.util.Map;

public interface Element {

    /**
     * 返回元素内容
     *
     * @param headers 请求头
     * @return
     */
    String value(Map<String, Collection<String>> headers);
}
