package hellgate.common.domain.stage;

import hellgate.common.domain.BaseAuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Magic extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Enumerated
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
     * 消耗
     */
    private BigDecimal consume;

    private BigDecimal maxConsume;
    private BigDecimal power;
    private BigDecimal maxPower;

    /**
     * 描述模板。
     */
    @Column(length = 2000)
    private String template;

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
    }
}
