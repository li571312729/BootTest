package com.hefu.resources;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@ConfigurationProperties(prefix = "zoo.keeper")
@Data
public class ZookeeperParam {

    // 开启标志
    private boolean enabled;
    // 服务器地址
    private String server;
    // 命名空间，被称为ZNode
    private String namespace;
    // 权限控制，加密
    private String digest;
    // 会话超时时间
    private Integer sessionTimeoutMs;
    // 连接超时时间
    private Integer connectionTimeoutMs;
    // 最大重试次数
    private Integer maxRetries;
    // 初始休眠时间
    private Integer baseSleepTimeMs;
}
