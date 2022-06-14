package hellgate.common.account;

import hellgate.common.audit.AuditableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 身份证。
 * <p>
 * 主要用于绑定账号，检测是否未成年，如果是未成年，则应该限制游戏时长。
 * <p>
 * 注意：仅适用于中国居民身份证，其他身份证可能不适用此实体。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class IdCard extends AuditableEntity {

    /**
     * 身份证号码。
     */
    @Column(nullable = false, unique = true)
    private String number;
    /**
     * 身份证姓名。
     */
    @Column(nullable = false)
    private String fullName;
    /**
     * 持有此身份证的账号列表。
     */
    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    private List<Account> holders;
}
