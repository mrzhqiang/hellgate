package com.github.mrzhqiang.hellgate.account;


import com.github.mrzhqiang.hellgate.actor.Actor;
import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import com.github.mrzhqiang.hellgate.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * 剧本。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Script extends BaseIdEntity implements GrantedAuthority {

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Stage stage;

    @Column(nullable = false)
    private Instant lastActive = Instant.now();

    /**
     * 指定服务器下创建的游戏角色。
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Actor> actors;

    @Override
    public String getAuthority() {
        return stage.getName();
    }
}
