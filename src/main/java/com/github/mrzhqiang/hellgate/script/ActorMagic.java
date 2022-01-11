package com.github.mrzhqiang.hellgate.script;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import com.github.mrzhqiang.hellgate.stage.Magic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ActorMagic extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Magic magic;
    private Integer level;
    private Integer skilled;

}
