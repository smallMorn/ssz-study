package com.ssz.mul.constants;

public interface MulConstant {

    String DISCOVERY_PREFIX = "ssz.discovery";

    /**
     * 默认标签字段命名
     */
    String ROUTE_FIELD_NAME = "route";


    /**
     * 是否生效
     */
    String ENABLED = "enabled";

    /**
     * 默认的registerId
     */
    String DEFAULT_REGISTER_ID = "mul_default";

    String LOADBALANCER_PREFIX = "mul.loadbalancer";

    String DEFAULT_ROUTE = "default";

    /**
     * 拼接符
     */
    String JOINT = "&&";


    /**
     * 默认实例缓存时间 单位/秒
     */
    int DEFAULT_PULL_INTERVAL = 5;

}
