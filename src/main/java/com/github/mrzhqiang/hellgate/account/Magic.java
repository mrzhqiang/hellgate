package com.github.mrzhqiang.hellgate.account;

import com.github.mrzhqiang.hellgate.common.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

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
     * 技能延迟，前摇。
     */
    private Integer delay;
    /**
     * 技能间隔，CD。
     */
    private Integer interval;
    /**
     * 攻击范围：1 单体、1+ 群体。
     * 另：
     */
    private Integer range;
    /**
     * 技能消耗
     */
    private TypedValue consume;
    /**
     * 技能伤害：
     * 1 == (最小攻击/最小魔法 + Math.random * 最大攻击/最大魔法) + 等级 * 数值 == 伤害值；
     * 2 == (最小攻击/最小魔法 + Math.random * 最大攻击/最大魔法) * 等级 * 百分比(1 + N%) == 伤害值
     */
    private TypedValue power;
    /**
     * 技能熟练度
     */
    private TypedValue train;
    private Integer maxLevel;

    @Enumerated
    private Type type;

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
