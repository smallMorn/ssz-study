package com.ssz.mul.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import lombok.Data;

@Data
public class NacosRegister {
    /**
     * 唯一性id 不填会给默认值
     *
     * @see
     */
    private String registerId;

    /**
     * 远程地址
     */
    private String remoteAddress;

    /**
     * 元空间
     */
    private String namespace;

    /**
     * 账号
     *
     * @see NacosRegister#remoteAddress 为http形式链接时需要
     * 当remoteAddress为Http形式时又未配置则会取↓
     * @see NacosDiscoveryProperties#getUsername() Nacos配置项
     */
    private String username;
    /**
     * 密码
     * @see NacosRegister#username 注释同理
     */
    private String password;
}
