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

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Account extends BaseIdEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean locked;
    private Boolean enabled = true;

    /**
     * 上次登录的服务器。
     * <p>
     * 冗余字段，省却遍历 servers 的操作。
     */
    @OneToOne(cascade = CascadeType.ALL)
    private UserServer lastTimeServer;

    /**
     * 创建过角色的服务器。
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
