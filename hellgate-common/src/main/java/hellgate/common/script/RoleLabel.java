package hellgate.common.script;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class RoleLabel extends AuditableEntity {

    private String name;
    private Integer level;

    @ManyToOne
    private Role role;
}
