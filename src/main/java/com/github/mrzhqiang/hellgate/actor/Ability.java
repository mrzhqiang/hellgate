package com.github.mrzhqiang.hellgate.actor;

import com.github.mrzhqiang.hellgate.domain.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ability")
public class Ability extends AuditableEntity {

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
