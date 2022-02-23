package hellgate.controller.script;

import hellgate.common.domain.BaseAuditableEntity;
import hellgate.controller.stage.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ToString.Exclude
    private Set<ActorMagic> magics;

    @OneToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Ability ability;
}
