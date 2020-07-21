package com.github.mrzhqiang.hellgateapi.model.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_server")
public class UserServer extends BaseIdEntity implements GrantedAuthority {
    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Server server;

    /**
     * 指定服务器下创建的游戏角色。
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Actor> actors;

    @Override
    public String getAuthority() {
        return server.getName();
    }
}
