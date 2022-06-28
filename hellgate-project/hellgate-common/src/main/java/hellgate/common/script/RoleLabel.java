package hellgate.common.script;

import hellgate.common.domain.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 剧本角色标签。
 * <p>
 * 就是每一个等级作为一个阶段，然后需要设置一个标签。
 * <p>
 * 比如 1 级萌新，100 级老兵，200 级龙骑士（战士） 之类。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class RoleLabel extends AuditableEntity {

    /**
     * 标签名称。
     */
    private String name;
    /**
     * 标签对应的等级。
     */
    private Integer level;

    /**
     * 标签所属角色。
     */
    @ManyToOne
    private ScriptRole role;
}
