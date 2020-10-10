package com.github.mrzhqiang.hellgate.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 基础的 ID 审计实体。
 * <p>
 * ID 默认是自增长的策略，如果有其他需求，请自行实现。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseIdAuditEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 2000)
    private String description;
}
