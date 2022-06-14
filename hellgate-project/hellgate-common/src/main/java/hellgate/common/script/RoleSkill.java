package hellgate.common.script;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 角色技能。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class RoleSkill extends AuditableEntity {

    /**
     * 技能名称。
     */
    private String name;
    /**
     * 技能图标。
     */
    private String icon;
    /**
     * 技能类型。
     *
     * <p>
     * 决定技能是否可以主动释放，或是否持续生效，等等。
     */
    @Enumerated(EnumType.STRING)
    private Type type;
    /**
     * 技能延迟。
     *
     * <p>
     * 释放后多久开始生效。
     */
    private Integer delay;
    /**
     * 冷却时间。
     *
     * <p>
     * 释放一次后，多久（回合）才可以继续释放。
     */
    private Integer coolDown;
    /**
     * 攻击距离：0，自身；1 单体；1+ 群体及目标数量。
     */
    private Integer distance;
    /**
     * 持续时间：0，作用一次；1+，持续作用，单位：回合。
     */
    private Integer duration;
    /**
     * 1 -- maxLevel
     */
    private Integer maxLevel;
    /**
     * 最小消耗
     */
    private Integer minConsume;
    /**
     * 最大消耗。
     */
    private Integer maxConsume;
    /**
     * 最小伤害。
     */
    private Integer minPower;
    /**
     * 最大伤害。
     */
    private Integer maxPower;
    /**
     * 简介。
     */
    private String description;
    /**
     * 消息模板。
     */
    private String template;

    /**
     * 技能类型。
     */
    public enum Type {
        /**
         * 攻击。
         */
        ATTACK,
        /**
         * 被动。
         */
        PASSIVE,
        /**
         * 状态。
         */
        STATUS,
    }
}
