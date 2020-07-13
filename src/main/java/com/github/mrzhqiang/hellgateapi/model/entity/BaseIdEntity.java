package com.github.mrzhqiang.hellgateapi.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * 基础的 ID 实体。
 * <p>
 * ID 默认是自增长的策略，如果有其他需求，请自行实现。
 */
@Getter
@Setter
@MappedSuperclass
abstract class BaseIdEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseIdEntity)) return false;
        BaseIdEntity that = (BaseIdEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
