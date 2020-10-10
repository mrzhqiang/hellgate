package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Ability extends BaseIdEntity {
    /**
     * 等级。
     */
    private Integer level;
    /**
     * 当前经验值。
     */
    private Long experience;
    /**
     * 知识。
     */
    private Long knowledge;
    private Integer currentHP;
    private Integer currentMP;
    private Integer maxHP;
    private Integer maxMP;
}
