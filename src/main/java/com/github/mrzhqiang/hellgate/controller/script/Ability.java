package com.github.mrzhqiang.hellgate.controller.script;

import com.github.mrzhqiang.hellgate.common.domain.BaseAuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Ability extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 等级。
     */
    private Integer level;
    /**
     * 当前经验值。
     */
    private Long exp;

    private Long maxExp;
    /**
     * 知识。
     */
    private Long knowledge;

    private Integer hp;
    private Integer mp;
    private Integer maxHP;
    private Integer maxMP;
}
