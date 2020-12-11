package com.github.mrzhqiang.hellgate.actor;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import com.github.mrzhqiang.hellgate.common.TypedValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.Duration;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Magic extends BaseIdEntity {
    /**
     * 技能名称
     */
    private String name;
    /**
     * 技能图标
     */
    private String icon;
    /**
     * 技能类型：主动、被动、状态。
     */
    private Type type;
    /**
     * 技能延迟，前摇。
     */
    private Duration delay;
    /**
     * 技能间隔，CD。
     */
    private Duration cooling;
    /**
     * 攻击距离：0，自身；1 单体；1+ 群体及目标数量。
     */
    private Integer distance;
    /**
     * 持续时间：0，立即作用；1+，持续作用。
     */
    private Duration duration;
    /**
     * 技能消耗
     */
    @OneToOne
    private TypedValue consume;
    /**
     * 技能伤害：power.computer(level) == 伤害值。
     */
    @OneToOne
    private TypedValue power;
    /**
     * 技能培养：即熟练度目标。
     * <p>
     * 如果 skilled 满足计算后的培养目标，那么就可以自动升级技能。
     * <p>
     * 另外，需要支持培养体系，才能通过战斗积累 skilled 来满足 train 的需求。
     */
    @OneToOne
    private TypedValue train;
    /**
     * 最大限制等级。
     */
    private Integer maxLevel;

    public enum Type {
        /**
         * 攻击技能。
         */
        ATTACK,
        /**
         * 被动技能。
         */
        PASSIVE,
        /**
         * 状态技能。
         */
        STATUS,
        ;
    }
}
