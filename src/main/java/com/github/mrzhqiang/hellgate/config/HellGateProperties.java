package com.github.mrzhqiang.hellgate.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ToString
@ConfigurationProperties("hellgate")
public class HellGateProperties {

    /**
     * 登录失败统计的持续时长。
     * <p>
     * 超过此区间时长，则重置统计次数。
     */
    private Duration firstFailedDuration;
    /**
     * 最大登录失败次数。
     */
    private Integer maxLoginFailed;
    /**
     * 锁定持续时长。
     */
    private Duration lockedDuration;
}
