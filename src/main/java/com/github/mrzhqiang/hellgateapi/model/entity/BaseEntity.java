package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

/**
 * 基础的实体。
 * <p>
 * 包含 version 用来实现乐观锁。
 * <p>
 * 包含 created 和 updated 用来排序和记录时间戳。
 */
@Getter
@Setter
@MappedSuperclass
abstract class BaseEntity {
    @Column(length = 2000)
    private String description;
    @Version
    private Long version;
    @CreationTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant updated;
}
