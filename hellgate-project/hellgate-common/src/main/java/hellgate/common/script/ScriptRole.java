package hellgate.common.script;

import hellgate.common.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 剧本角色。
 * <p>
 * 这是一个设计角色，每个剧本角色都是固定的数值。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ScriptRole extends AuditableEntity {

    /**
     * 角色名称。
     */
    private String name;
    /**
     * 角色类型。
     * <p>
     * 主要借鉴 Diablo Ⅲ 的设定，角色类型对应的属性具有一定的伤害百分比加成。
     */
    private Type type;
    /**
     * 初始 HP 值。
     */
    private Integer initHp;
    /**
     * 成长 HP 值。
     */
    private Integer growHp;
    /**
     * HP 比率。
     */
    private Integer hpRatio;
    /**
     * 初始 MP 值。
     */
    private Integer initMp;
    /**
     * 成长 MP 值。
     */
    private Integer growMp;
    /**
     * MP 比率。
     */
    private Integer mpRatio;
    /**
     * 初始力量属性。
     */
    private Integer initSTR;
    /**
     * 力量属性转化比率。
     */
    private Integer strRatio;
    /**
     * 初始敏捷属性。
     */
    private Integer initDEX;
    /**
     * 敏捷属性转化比率。
     */
    private Integer dexRatio;
    /**
     * 初始智力属性。
     */
    private Integer initINT;
    /**
     * 智力属性转化比率。
     */
    private Integer intRatio;
    /**
     * 初始体质属性。
     */
    private Integer initCON;
    /**
     * 体质属性转化比率。
     */
    private Integer conRatio;
    /**
     * 角色所在剧本。
     */
    @ManyToOne
    private Script script;
    /**
     * 角色标签列表。
     */
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private List<RoleLabel> labels;
    /**
     * 角色可学习的技能列表。
     */
    @ManyToMany
    @ToString.Exclude
    private List<RoleSkill> roleSkills;

    /**
     * 角色类型。
     */
    public enum Type {
        /**
         * 力量角色。
         * <p>
         * 力量影响攻击力、防御、HP 上限。
         */
        STR,
        /**
         * 敏捷角色。
         * <p>
         * 敏捷影响攻击力、速度、闪躲。
         */
        DEX,
        /**
         * 智力角色。
         * <p>
         * 智力影响魔法系数、抗性、MP 上限。
         */
        INT,
        /**
         * 体质角色。
         * <p>
         * 体质影响 HP 上限、MP 上限、防御、抗性。
         */
        CON,
    }
}
