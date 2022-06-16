package com.ssz.mul.ribbon;

import java.util.Collection;
import java.util.Map;

public class HeaderThreadLocal<T> {

    private final ThreadLocal<T> local = new ThreadLocal<>();

    /**
     * 子类处理后返回集合
     *
     * @return 一般是header
     */
    public Map<String, Collection<String>> getHeaders() {
        throw new UnsupportedOperationException();
    }

    public T getLocal() {
        return local.get();
    }

    public void setLocal(T obj) {
        this.local.set(obj);
    }

    public void removeLocal() {
        local.remove();
    }

}
