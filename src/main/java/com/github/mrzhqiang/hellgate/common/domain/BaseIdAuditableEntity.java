package com.github.mrzhqiang.hellgate.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * 基础的 ID + 可审计实体。
 * <p>
 * 包含 Long 型 ID 主键。
 */
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
public abstract class BaseIdAuditableEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseIdAuditableEntity that = (BaseIdAuditableEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
