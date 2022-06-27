package com.ssz.mul.ribbon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HeaderThreadLocal<T> {

    private final ThreadLocal<Map<String, Object>> local = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public Map<String, Object> getLocal() {
        return local.get();
    }

    public void setLocal(String key, Object value) {
        this.local.get().put(key, value);
    }

    public void removeLocal() {
        local.remove();
    }

}
