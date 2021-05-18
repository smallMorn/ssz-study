package com.ssz.common.web.cache;

import com.google.common.base.Joiner;

/**
 * Date 2021/5/12 14:47
 * Author ssz
 */
public enum CacheKey {

    LIST_USER(1,Unit.WEEK),

    KV_USER_DETAIL(1,Unit.DAY);

    private static final String SEPARATOR = ":";
    private static final String LOCK_KEY_PREFIX = "LOCK_";

    private Integer ttl;

    CacheKey(Integer amount, Unit unit) {
        this.ttl = amount * unit.getValue();
    }

    public String key() {
        return this.name().toLowerCase();
    }

    public String key(Object... suffix) {
        return this.name().toLowerCase() + SEPARATOR + Joiner.on(SEPARATOR).join(suffix);
    }

    public String lockKey() {
        return LOCK_KEY_PREFIX + key();
    }
    public String lockKey(Object... suffix) {
        return LOCK_KEY_PREFIX + key(Joiner.on(SEPARATOR).join(suffix));
    }

    public Integer getTtl() {
        return ttl;
    }

    private enum Unit {

        SECOND(1), MINUTE(60), HOUR(60 * 60), DAY(60 * 60 * 24), WEEK(60 * 60 * 24 * 7), MONTH(60 * 60 * 24 * 30);

        private Integer value;

        Unit(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
