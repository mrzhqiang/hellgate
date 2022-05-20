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

@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Script extends AuditableEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Type type = Type.NEW;
    private String label;
    private String url;
    private boolean active = true;
    /**
     * 推荐指数：1 -- 100，default 1
     */
    private Integer recommend = 1;
    /**
     * 热门积分：1000 * (在线人数 / (注册人数 + 1))
     */
    private Integer popularity = 0;

    @OneToMany(mappedBy = "script")
    @ToString.Exclude
    private List<Role> roles;

    public enum Type {
        /**
         * 新区，刚开一秒。
         */
        NEW("Script.Type.NEW"),
        /**
         * 热门，人气火爆。
         */
        POPULAR("Script.Type.POPULAR"),
        /**
         * 老区，长久稳定。
         */
        PERPETUAL("Script.Type.PERPETUAL"),
        /**
         * 冷门，即将合区。
         */
        MERGE_SOON("Script.Type.MERGE_SOON"),
        ;

        final String messageCode;

        Type(String messageCode) {
            this.messageCode = messageCode;
        }

        public String getMessageCode() {
            return messageCode;
        }
    }
}
