package hellgate.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * 基础的可审计实体。
 * <p>
 * 审计信息是为了查证对当前实体的最近一次操作，需要记录操作日志和埋点统计信息，应另外提供数据库表。
 */
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditableEntity extends BaseEntity {

    /**
     * 创建人。
     * <p>
     * 记录的是用户名，而不是用户 ID。
     * <p>
     * 如果是 ManyToOne 的方式，则可能导致 ToString 时， Account 实体出现死循环。
     */
    @CreatedBy
    private String createdBy;
    /**
     * 最近修改人。
     */
    @LastModifiedBy
    private String lastModifiedBy;
}
