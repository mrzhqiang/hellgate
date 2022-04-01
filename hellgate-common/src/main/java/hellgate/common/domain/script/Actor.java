package hellgate.common.domain.script;

import hellgate.common.domain.BaseAuditableEntity;
import hellgate.common.domain.stage.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Actor extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
