package hellgate.common.model.stage;

import hellgate.common.model.AuditableEntity;
import hellgate.common.model.script.Script;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Stage extends AuditableEntity {

    private String name;
    private String label;
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
    private Set<Role> roles;

    @OneToMany
    @ToString.Exclude
    private List<Script> actives;
}
