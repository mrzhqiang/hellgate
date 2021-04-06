package com.github.mrzhqiang.hellgate.actor;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Job extends AuditableEntity {

    @Id
    private String name;
    private Integer type;

    /**
     * 职业可学习技能。
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Magic> magics;
}
