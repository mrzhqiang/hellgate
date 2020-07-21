package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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
     * 上次登录的服务器。
     * <p>
     * 冗余字段，省却遍历 servers 的操作。
     */
    @OneToOne(cascade = CascadeType.ALL)
    private UserServer lastTimeServer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserServer> servers;

    /**
     * 必须在指定服务器建立过角色，才能访问对应的页面。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return servers == null ? Collections.emptyList() : servers;
    }

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
}
