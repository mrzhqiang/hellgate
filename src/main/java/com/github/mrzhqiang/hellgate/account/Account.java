package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
public class Account extends BaseIdEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean locked = false;
    private Boolean enabled = true;

    /**
     * 上次登录的游戏服务。
     * <p>
     * 冗余字段，省却遍历操作。
     */
    @OneToOne(cascade = CascadeType.ALL)
    private UserServer lastTimeServer;

    /**
     * 创建过角色的游戏服务列表。
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<UserServer> servers;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return servers == null ? Collections.emptyList() : servers;
    }
}
