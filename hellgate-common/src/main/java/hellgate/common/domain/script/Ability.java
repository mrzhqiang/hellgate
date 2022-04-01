package hellgate.common.domain.script;

import hellgate.common.domain.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Ability extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 等级。
     */
    private Integer level;
    /**
     * 当前经验值。
     */
    private Long exp;

    private Long maxExp;
    /**
     * 知识。
     */
    private Long knowledge;

    private Integer hp;
    private Integer mp;
    private Integer maxHP;
    private Integer maxMP;
}
