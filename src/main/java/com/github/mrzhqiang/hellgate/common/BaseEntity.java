package com.github.mrzhqiang.hellgate.common;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Version
    private Long version;
    @CreationTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant updated;
}
