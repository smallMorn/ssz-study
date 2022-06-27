package com.ssz.mul.element;

import com.ssz.mul.constants.MulConstant;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface Element {

    /**
     * 返回元素内容
     *
     * @param map 请求头
     * @return
     */
    default String get(Map<String, Object> map) {
        return MulConstant.HINT;
    }

    default String gain(Map<String, Object> map, String key, String defVal) {
        return gain(map, key, () -> defVal);
    }

    default String gain(Map<String, Object> map, String key, Supplier<String> defVal) {
        Object value = map.get(key);
        if (value instanceof String) {
            String result = (String) value;
            return result.isEmpty() ? defVal.get() : result;
        }
        return defVal.get();
    }

}
