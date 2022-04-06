package hellgate.common.model.stage;

import hellgate.common.model.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Role extends AuditableEntity {

    private String name;
    private Integer type;

    @ManyToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Magic> magics;
}
