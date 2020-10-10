package com.github.mrzhqiang.hellgate.account;


import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
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
