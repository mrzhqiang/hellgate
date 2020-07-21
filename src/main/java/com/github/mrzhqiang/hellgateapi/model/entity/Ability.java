package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
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
