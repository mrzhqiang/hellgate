package com.github.mrzhqiang.hellgate.script;


import com.github.mrzhqiang.hellgate.actor.Actor;
import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import com.github.mrzhqiang.hellgate.stage.Stage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * 剧本。
 * <p>
 * 一个剧本有很多个角色。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Script extends AuditableEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Stage stage;

    @Column(nullable = false)
    private Instant lastActive = Instant.now();

    /**
     * 指定服务器下创建的游戏角色。
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Actor> actors;

    @Override
    public String getAuthority() {
        return stage.getName();
    }
}
