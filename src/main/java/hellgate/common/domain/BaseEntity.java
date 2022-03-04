package hellgate.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 基础实体。
 * <p>
 * 最底层的实体，包括创建时间、最近修改时间以及用于乐观锁的版本号。
 * <p>
 * 创建时间可以帮助排序，最近修改时间可以辅助审计，而乐观锁应用于控制并发情况，将有效降低脏数据出现的可能性。
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * 创建时间。
     */
    @CreatedDate
    private LocalDateTime created;
    /**
     * 最后修改时间。
     */
    @LastModifiedDate
    private LocalDateTime lastModified;
    /**
     * 版本号。
     */
    @Version
    private Long version;
}
