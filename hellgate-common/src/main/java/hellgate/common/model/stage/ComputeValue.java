package hellgate.common.model.stage;

import hellgate.common.model.AuditableEntity;
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
