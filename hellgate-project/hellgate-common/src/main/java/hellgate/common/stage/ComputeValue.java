package hellgate.common.stage;

import hellgate.common.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * 计算值。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ComputeValue extends AuditableEntity {

    private String name;
}
