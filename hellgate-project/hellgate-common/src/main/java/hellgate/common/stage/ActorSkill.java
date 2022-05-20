package hellgate.common.stage;

import hellgate.common.audit.AuditableEntity;
import hellgate.common.script.Skill;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ActorSkill extends AuditableEntity {

    @OneToOne(optional = false)
    private Skill skill;

    private Integer level;
    private Integer skilled;
}
