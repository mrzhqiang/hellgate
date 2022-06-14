package hellgate.common.script;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 剧本。
 * <p>
 * 一个剧本代表一个可访问的服务器。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Script extends AuditableEntity {

    /**
     * 剧本名称。
     * <p>
     * 通常由两个字到四个字组成，方便展示为剧本列表。
     */
    private String name;
    /**
     * 剧本类型。
     * <p>
     * 展示在剧本名称之后，用方括号 [ 及 ] 括起来。
     */
    @Enumerated(EnumType.STRING)
    private Type type = Type.NEW;
    /**
     * 自定义标签。
     * <p>
     * 如果要覆盖剧本类型，那么就可以设置一个自定义标签。
     */
    private String label;
    /**
     * 剧本的访问链接。
     */
    private String url;
    /**
     * 剧本是否激活。
     * <p>
     * 用来控制剧本是否展示在列表中，作为对玩家是否可见的开关。
     */
    private boolean active = true;
    /**
     * 推荐指数：1 -- 100，default 1
     */
    private Integer recommend = 1;
    /**
     * 热门积分：1000 * (在线人数 / (注册人数 + 1))
     */
    private Integer popularity = 0;

    /**
     * 剧本包含的角色。
     * <p>
     * 在本系统中，剧本角色就是职业的代名词。
     */
    @OneToMany(mappedBy = "script")
    @ToString.Exclude
    private List<ScriptRole> roles;

    /**
     * 剧本类型。
     * <p>
     * 一般来说，每隔三个月出现一个新的剧本。
     * <p>
     * 但我们可以简化这种判断，即只要没有新的剧本诞生，那么最近一次诞生的剧本，它的类型始终是新区。
     * <p>
     * 反过来说，最老诞生的剧本，它的类型始终是老区。
     * <p>
     * 抛开新区和老区，综合注册人数和在线人数的一定比例作为热门积分，则积分数值最高的设定为热门。反之，设定为冷门。
     * <p>
     * 一旦冷门的持续时间超过一定阈值，比如三个月，则我们将其标记为【待合区】状态，具体是否合区，由玩家投票决定。
     * <p>
     * 在剧本数量达到一定阈值时，比如 5 个剧本，则触发合区模式，合区周期与新区保持一致，即一般情况下每三个月触发一次。
     */
    public enum Type {
        /**
         * PTR
         * <p>
         * Public Test Realm 公测服务器。
         * <p>
         * 用来测试新赛季开发的一些内容，限量提供测试资格，拥有超高倍经验和高倍爆率，不开放商城但材料可兑换。
         * <p>
         * 服务器内可以反馈 BUG 内容，一旦被采纳且定级，将提供一定的账号充值额度作为奖励。
         * <p>
         * 如果在同一时间内反馈的内容相同，则以被标记为有效反馈的内容为主，其他反馈将合并到此内容，以示精神奖励。
         */
        PTR("公测、奖励"),
        /**
         * 全新、特色。
         * <p>
         * PTR 结束后，空闲一段时间，将开放新的服务器，作为新内容的沉淀测试。
         * <p>
         * 只有新区冲刺活动，没有每周轮换、没有节日专属，一般持续两个月时间。
         * <p>
         * 被替代的前一次服务器，将根据人气值是否达到热门阈值，而决定是否归入热门服务器还是长久服务器。
         */
        NEW("r全新、特色"),
        /**
         * 热门、火爆。
         * <p>
         * 唯一开放充值的服务器，不定时开放充值优惠活动。
         * <p>
         * 不会与长久服务器进行合区，游戏内容从退位的全新服务器继承而来，每周轮换和节日专属等活动与长久服务器保持一致。
         */
        POPULAR("热门、火爆"),
        /**
         * 长久、稳定。
         * <p>
         * 通过运营热门服务器产生的利润和交易系统的手续费，提供一定比例的费用用来运营此服务器，给广大散人骨灰玩家一个天堂。
         * <p>
         * 不会与热门服务器进行合区，游戏内容从退位的全新服务器继承而来，每周轮换和节日专属等活动与长久服保持一致。
         * <p>
         * 唯一与热门服务器的区别就是不能充值，其他完全一致。
         */
        PERPETUAL("长久、稳定"),
        ;

        final String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
