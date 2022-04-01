package hellgate.common.domain.script;

import hellgate.common.domain.BaseAuditableEntity;
import hellgate.common.domain.stage.Magic;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ActorMagic extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Magic magic;

    private Integer level;
    private Integer skilled;
}
