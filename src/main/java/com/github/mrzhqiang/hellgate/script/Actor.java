package com.github.mrzhqiang.hellgate.script;

import com.github.mrzhqiang.hellgate.common.domain.BaseAuditableEntity;
import com.github.mrzhqiang.hellgate.stage.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Actor extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Set<ActorMagic> magics;

    @OneToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Ability ability;
}
