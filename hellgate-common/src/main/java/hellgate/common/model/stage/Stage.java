package hellgate.common.model.stage;

import hellgate.common.model.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 舞台。
 * <p>
 * 一般表示为不同的服务器。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Stage extends AuditableEntity {

    private String name;
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

    /**
     * 舞台标签。
     */
    public enum Label {
        NEW("新区"),
        POPULAR("热门"),
        LONG_TERM_OPEN("稳定"),
        ;

        final String message;

        Label(String message) {
            this.message = message;
        }
    }
}
