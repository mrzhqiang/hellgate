package hellgate.common.model.script;

import hellgate.common.model.AuditableEntity;
import hellgate.common.model.stage.Magic;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ActorMagic extends AuditableEntity {

    @OneToOne(optional = false)
    private Magic magic;

    private Integer level;
    private Integer skilled;
}
