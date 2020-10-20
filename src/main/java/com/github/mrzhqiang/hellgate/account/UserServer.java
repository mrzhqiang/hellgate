package com.github.mrzhqiang.hellgate.account;


import com.github.mrzhqiang.hellgate.account.actor.Actor;
import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import com.github.mrzhqiang.hellgate.account.server.Server;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * 游戏服务。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class UserServer extends BaseIdEntity implements GrantedAuthority {

    @OneToOne(cascade = CascadeType.ALL, optional = false)
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
