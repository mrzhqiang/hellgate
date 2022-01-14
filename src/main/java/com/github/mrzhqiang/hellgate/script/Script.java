package com.github.mrzhqiang.hellgate.script;

import com.github.mrzhqiang.hellgate.common.domain.BaseIdSoftDeleteAuditableEntity;
import com.github.mrzhqiang.hellgate.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 剧本。
 * <p>
 * 专属于个人的剧本，包含对应的舞台，以及玩家在剧本中创建的角色。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Script extends BaseIdSoftDeleteAuditableEntity {

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Stage stage;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ToString.Exclude
    private Set<Actor> actors;
}
