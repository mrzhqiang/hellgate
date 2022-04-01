package hellgate.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * 基础的可审计实体。
 * <p>
 * 审计信息可以溯源创建人和最近修改人。
 * <p>
 * 需要记录操作日志以及埋点统计信息的话，应使用单独的数据源，而不是审计字段。
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
     * 一般推荐记录 {@link UserDetails#getUsername() 用户名} ，而不是 id 值。
     * <p>
     * 注意：不建议使用 ManyToOne 注解，因为它将导致 Account 在 ToString 时，出现死循环。
     * 要解决这个问题，必须将此字段排除在 ToString 之外。
     */
    @CreatedBy
    private String createdBy;
    /**
     * 最近修改人。
     * <p>
     * 一般推荐记录 {@link UserDetails#getUsername() 用户名} ，而不是 id 值。
     * <p>
     * 注意：不建议使用 ManyToOne 注解，因为它将导致 Account 在 ToString 时，出现死循环。
     * 要解决这个问题，必须将此字段排除在 ToString 之外。
     */
    @LastModifiedBy
    private String lastModifiedBy;
}
