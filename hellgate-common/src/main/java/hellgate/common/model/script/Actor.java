package hellgate.common.model.script;

import hellgate.common.model.AuditableEntity;
import hellgate.common.model.stage.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Actor extends AuditableEntity {

    @Column(length = 20)
    private String name;

    @ManyToOne(optional = false)
    private Role role;

    @OneToMany
    @ToString.Exclude
    private Set<ActorMagic> magics;

    @OneToOne(optional = false)
    private Ability ability;
}
