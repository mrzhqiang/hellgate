package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "actor")
public class Actor extends BaseIdEntity {
    @Column(unique = true, nullable = false, length = 20)
    private String name;
    /**
     * 职业。
     */
    private Job job;
    /**
     * 能力、属性。
     */
    private Ability ability;
}
