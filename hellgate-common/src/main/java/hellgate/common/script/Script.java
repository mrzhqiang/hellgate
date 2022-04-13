package hellgate.common.script;

import hellgate.common.domain.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Script extends AuditableEntity {

    private String name;
    private String path;
    @Enumerated(EnumType.STRING)
    private Label label = Label.NEW;
    private boolean active = true;
    /**
     * 推荐指数：1 -- 100，default 1
     */
    private Integer recommend = 1;
    /**
     * 热门积分：1000 * (在线人数 / (注册人数 + 1))
     */
    private Integer popularity = 0;

    @OneToMany
    @ToString.Exclude
    private List<Role> roles;

    public enum Label {
        /**
         * 新区开放
         */
        NEW,
        /**
         * 流行热门
         */
        POPULAR,
        /**
         * 长久稳定
         */
        STABLE,
        /**
         * 即将合并
         */
        WILL_MERGE,
        /**
         * 已经关闭
         */
        HAS_CLOSED,
    }
}
