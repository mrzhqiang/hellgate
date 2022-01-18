package com.github.mrzhqiang.hellgate.controller.account;

import com.github.mrzhqiang.hellgate.common.domain.BaseIdSoftDeleteAuditableEntity;
import com.github.mrzhqiang.hellgate.controller.script.Script;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * 账户实体。
 */
@Getter
@Setter
@ToString(callSuper = true)
@SQLDelete(sql = "update account set deleted = true where id = ?")
@Entity
public class Account extends BaseIdSoftDeleteAuditableEntity implements UserDetails {

    @Column(updatable = false, unique = true, nullable = false)
    private String username;
    /**
     * 存储已编码的密码。
     *
     * 密码绝对不允许明文存储，通过 Spring Security 可以做到游戏的
     */
    @Column(nullable = false)
    private String password;

    /**
     * 首次认证失败的时间。
     * <p>
     * 记录首次失败时间，可以在一定持续时间内，统计认证失败的次数，在达到最大次数时，锁定账号一段时间，避免被暴力破解。
     */
    private LocalDateTime firstFailed;
    /**
     * 失败次数。
     * <p>
     * 简单的统计失败次数，失败次数的上限阈值一般从程序配置中获取。
     */
    private int failedCount = 0;
    /**
     * 锁定时间。
     * <p>
     * 第一次登录时，锁定时间不存在；触发锁定账户之后，只要比当前时间早一定的阈值，则不属于锁定状态。
     * <p>
     * 这种逻辑可以避免锁定状态的循环检测。
     */
    private LocalDateTime locked;
    /**
     * 是否禁用。
     * <p>
     * 禁用账户比锁定账户更严重，因为这意味着，如果不手动启用，账户将始终无法使用。
     */
    private boolean disabled;

    /**
     * 最近玩过的剧本。
     */
    @OneToOne
    private Script lastScript;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked == null || LocalDateTime.now().isAfter(locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
