package com.github.mrzhqiang.hellgate.account.actor;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ability")
public class Ability extends BaseIdEntity {
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
