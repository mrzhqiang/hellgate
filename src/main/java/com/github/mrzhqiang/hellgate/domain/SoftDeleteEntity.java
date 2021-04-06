package com.github.mrzhqiang.hellgate.domain;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@Where(clause = "deleted=false")
public abstract class SoftDeleteEntity {

    /**
     * 此标识表示：是否逻辑删除。
     * <p>
     * 通常情况下，删除操作表示物理删除，要使用逻辑删除，需要手动设置此值为 true，以区分物理删除操作。
     */
    private boolean deleted = false;

}
