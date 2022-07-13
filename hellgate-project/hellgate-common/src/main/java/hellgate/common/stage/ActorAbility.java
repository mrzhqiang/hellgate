package hellgate.common.stage;

import hellgate.common.domain.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * 演员能力。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class ActorAbility extends AuditableEntity {

    /**
     * 基础 HP 值。
     * <p>
     * 基础表示存档属性，经过一系列装备和技能加成之后，才得到面板属性。
     */
    private BigDecimal hp;
    /**
     * 基础 MP 值。
     */
    private BigDecimal mp;
    /**
     * 基础 HP 恢复值。
     */
    private BigDecimal hpRecovery;
    /**
     * 基础 MP 恢复值。
     */
    private BigDecimal mpRecovery;
    /**
     * 基础攻击。
     * <p>
     * 参考 Diablo Ⅲ 的武器攻击及伤害类型，最终计算成为面板伤害、回合伤害和最终伤害。
     */
    private BigDecimal attack;
    /**
     * 基础防御。
     * <p>
     * 参考 Diablo Ⅲ 的护甲值，通过 TypedValue 的 Type.FACTOR 算法可以换算成为减伤比率。
     * <p>
     * 同时，根据减伤类型不同，计算方式也不一样，最终成为面板坚韧用于抵消最终伤害。
     */
    private BigDecimal defense;
    /**
     * 物理元素加成。
     * <p>
     * 隐藏破损特性，一定几率降低敌方的装备耐久度。
     */
    private BigDecimal physicsElement;
    /**
     * 冰霜元素加成。
     * <p>
     * 隐藏迟缓特性，一定几率降低敌方的速度一段时间。
     */
    private BigDecimal frostElement;
    /**
     * 闪电元素加成。
     * <p>
     * 隐藏传导特性，一定几率使敌方单体承受全额伤害，敌方群体以命中点为中心，逐级平分伤害。
     */
    private BigDecimal lightningElement;
    /**
     * 火焰元素加成。
     * <p>
     * 隐藏灼烧特性，一定几率使敌方持续掉血一段时间。
     */
    private BigDecimal flameElement;
    /**
     * 风暴元素加成。
     * <p>
     * 隐藏疾速特性，一定几率使自身速度翻倍一段时间。
     */
    private BigDecimal stormElement;
    /**
     * 毒性元素加成。
     * <p>
     * 隐藏虚弱特性，一定几率使目标降低攻击、防御一段时间。
     */
    private BigDecimal toxicityElement;
    /**
     * 神圣元素加成。
     * <p>
     * 隐藏神罚特性，一定几率对恶魔、幽灵等黑暗位面的敌方造成当前血量百分比的额外伤害。
     */
    private BigDecimal sacredElement;
    /**
     * 物理抗性。
     */
    private BigDecimal physicsResistance;
    /**
     * 冰霜抗性。
     */
    private BigDecimal frostResistance;
    /**
     * 闪电抗性。
     */
    private BigDecimal lightningResistance;
    /**
     * 火焰抗性。
     */
    private BigDecimal flameResistance;
    /**
     * 风暴抗性。
     */
    private BigDecimal stormResistance;
    /**
     * 毒性抗性。
     */
    private BigDecimal toxicityResistance;
    /**
     * 神圣抗性。
     */
    private BigDecimal sacredResistance;
    /**
     * 速度。
     * <p>
     * 速度决定在每回合中，谁领先多少 ms 出手。
     */
    private Integer speed;
    /**
     * 闪避值。
     * <p>
     * 闪避值通过因子类型值转换为闪避率。
     * <p>
     * 可闪避的伤害类型一般是元素技能，包括群体技能。
     * <p>
     * 单体技能一般为锁定攻击，仅和命中率有关。
     */
    private BigDecimal dodge;
    /**
     * 感应值。
     * <p>
     * 消耗一定的感应可以百分百触发闪躲。
     */
    private BigDecimal sense;
    /**
     * 感应恢复速度。
     */
    private BigDecimal senseRecovery;
    /**
     * 命中值。
     * <p>
     * 命中值通过因子类型值转换为命中率。
     * <p>
     * 单体技能为锁定攻击，不可闪避，但有一定的命中率，需要通过运气提升命中。
     */
    private BigDecimal hit;
    /**
     * 运气值。
     * <p>
     * 消耗一定运气可以百分百命中敌人。
     */
    private BigDecimal luck;
    /**
     * 运气恢复速度。
     */
    private BigDecimal luckRecovery;
    /**
     * 格挡值。
     * <p>
     * 格挡值通过因子类型值转换为格挡率。
     * <p>
     * 可格挡的伤害类型一般是无属性的武器攻击，包括无锁定目标的战士技能。
     */
    private BigDecimal parry;
    /**
     * 耐力值。
     * <p>
     * 消耗一定耐力可以百分百格挡攻击。
     */
    private BigDecimal stamina;
    /**
     * 耐力恢复速度。
     */
    private BigDecimal staminaRecovery;
}
