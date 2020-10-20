package com.github.mrzhqiang.hellgate.account.actor;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Actor extends BaseIdEntity {

    @Column(unique = true, nullable = false, length = 20)
    private String name;
    /**
     * 职业。
     */
    @OneToOne
    private Job job;
    @OneToMany
    private Set<ActorMagic> magics;
    /**
     * 能力、属性。
     */
    @OneToOne
    private Ability ability;
}
