package com.github.mrzhqiang.hellgate.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 基础的 ID 实体。
 * <p>
 * ID 默认是自增长的策略，如果有其他需求，请自行实现。
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class BaseIdEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 2000)
    private String description;
}
