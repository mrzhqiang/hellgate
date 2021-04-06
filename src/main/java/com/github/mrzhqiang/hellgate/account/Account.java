package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import com.github.mrzhqiang.hellgate.script.Script;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 账户。
 * <p>
 * 用户名+密码即可登录。
 * <p>
 * 包含创建过角色的用户服务列表及上次访问的用户服务，主要是用来列出角色信息。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account")
public class Account extends AuditableEntity implements UserDetails {

    @Id
    @Column(updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    /**
     * 账号有效时间。
     * <p>
     * null 或者 valid is after now 表示账号有效
     */
    private Instant valid;
    /**
     * 密码检验失败次数统计。
     */
    private int failedCount = 0;
    /**
     * 锁定时间。
     * <p>
     * null 或者 locked is before now 表示账号未锁定
     */
    private Instant locked;
    /**
     * 证书过期时间。
     * <p>
     * null 或者 expired is after now 表示证书有效
     */
    private Instant expired;
    /**
     * 是否激活。
     * <p>
     * 推荐人激活、邮箱激活、手机验证码激活，等等。
     */
    private boolean enabled = true;

    /**
     * 剧本，创建过角色的游戏服务列表。
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Script> scripts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return scripts == null ? Collections.emptyList() : scripts;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.isNull(valid) || valid.isAfter(Instant.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.isNull(locked) || locked.isBefore(Instant.now());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Objects.isNull(expired) || expired.isAfter(Instant.now());
    }

}
