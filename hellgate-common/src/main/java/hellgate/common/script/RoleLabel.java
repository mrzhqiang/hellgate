package hellgate.common.script;

import hellgate.common.domain.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class RoleLabel extends AuditableEntity {

    private String name;
    private Integer require;
}
