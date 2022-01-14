package com.github.mrzhqiang.hellgate.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.MappedSuperclass;

/**
 * 基础的软删除 + 可审计实体。
 * <p>
 * 软删除可保证数据 id 连续性，从而保证相关性能不受损。
 * <p>
 * 通常情况下，删除操作表示物理删除，要使用逻辑删除，需要手动设置此值为 true，以区分物理删除操作。
 * <p>
 * 所以要在扩展的实体上，添加如下注解：
 * <p>
 * {@code @org.hibernate.annotations.SQLDelete(sql = "update $tableName$ set deleted = true where id = ?") }
 * <p>
 * 建议将以上代码加入到 IDEA 的 live template 中，提高开发效率。
 */
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
@Where(clause = "deleted = false")
public abstract class BaseSoftDeleteAuditableEntity extends BaseAuditableEntity {

    /**
     * 是否逻辑删除。
     */
    private boolean deleted;
}
