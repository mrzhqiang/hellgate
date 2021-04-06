package com.github.mrzhqiang.hellgate.actor;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
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
    @OneToOne(cascade = CascadeType.ALL)
    private Magic magic;
    private Integer level;
    private Integer skilled;

}
