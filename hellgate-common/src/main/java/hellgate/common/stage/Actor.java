package hellgate.common.stage;

import hellgate.common.domain.AuditableEntity;
import hellgate.common.script.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

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
    private List<ActorSkill> magics;

    @OneToOne(optional = false)
    private ActorAbility actorAbility;
}
