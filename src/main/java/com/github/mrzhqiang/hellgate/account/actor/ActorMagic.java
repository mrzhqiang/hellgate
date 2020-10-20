package com.github.mrzhqiang.hellgate.account.actor;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class ActorMagic extends BaseIdEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Magic magic;
    private Integer level;
    private Integer skilled;

}
