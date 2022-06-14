package hellgate.common.stage;

import hellgate.common.audit.AuditableEntity;
import hellgate.common.script.RoleSkill;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 演员技能。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ActorSkill extends AuditableEntity {

    /**
     * 所属角色技能。
     */
    @OneToOne(optional = false)
    private RoleSkill roleSkill;

    /**
     * 技能等级。
     */
    private Integer level;
    /**
     * 熟练度。
     */
    private Integer skilled;

    /**
     * 键位映射。
     * <p>
     * fixme 实际上它应该是一个实体对象或枚举值。
     */
    private Integer keyMap;
}
