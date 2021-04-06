package com.github.mrzhqiang.hellgate.actor;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

/**
 * 演员。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Actor extends AuditableEntity {

    @Id
    @Column(length = 20)
    private String name;
    /**
     * 职业。
     */
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Job job;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<ActorMagic> magics;

    /**
     * 能力、属性。
     */
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Ability ability;
}
