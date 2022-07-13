package hellgate.common.stage;

import hellgate.common.domain.AuditableEntity;
import hellgate.common.script.ScriptRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

/**
 * 演员。
 * <p>
 * 包含玩家、NPC 以及其他对剧本有重要作用的代表。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Actor extends AuditableEntity {

    /**
     * 默认情况下，每一级获得的能力点。
     */
    public static final int DEF_LV_AP = 5;

    /**
     * 自定义名称。
     */
    @Column(length = 20)
    private String name;
    /**
     * 等级。
     */
    private Integer level;
    /**
     * 巅峰等级。
     */
    private Integer peakLevel;
    /**
     * 当前经验值。
     */
    private BigDecimal exp;
    /**
     * 升级所需最大经验值。
     */
    private BigDecimal maxExp;
    /**
     * 已获得的知识点。
     */
    private Long knowledgePoint;
    /**
     * 对应的剧本角色。
     */
    @ManyToOne(optional = false)
    private ScriptRole role;
    /**
     * 当前的能力。
     */
    @OneToOne(optional = false)
    private ActorAbility ability;
    /**
     * 已学会的技能。
     */
    @OneToMany
    @ToString.Exclude
    private List<ActorSkill> skills;
    /**
     * 当前力量。
     */
    private Long currentSTR;
    /**
     * 当前敏捷。
     */
    private Long currentDEX;
    /**
     * 当前智力。
     */
    private Long currentINT;
    /**
     * 当前体质。
     */
    private Long currentCON;
    /**
     * 可用巅峰点。
     */
    private Long peakPoint;
}
