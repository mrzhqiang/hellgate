package hellgate.common.stage;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ComputeValue extends AuditableEntity {

    private String name;
}
