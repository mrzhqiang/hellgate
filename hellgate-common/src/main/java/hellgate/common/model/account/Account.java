package hellgate.common.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hellgate.common.model.AuditableEntity;
import hellgate.common.model.script.Script;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.Collection;

@Getter
@Setter
@ToString(callSuper = true)
@SQLDelete(sql = "update account set deleted = true where id = ?")
@Entity
public class Account extends AuditableEntity implements UserDetails {

    @Column(updatable = false, unique = true, nullable = false)
    private String username;
    /**
     * 存储已编码的密码。
     * <p>
     * 密码绝对不允许明文存储，通过 Spring Security 可以做到自动随机盐加密。
     */
    @JsonIgnore
    @ToString.Exclude
    @Column(nullable = false)
    private String password;
    /**
     * 首次认证失败的时间。
     * <p>
     * 记录首次失败时间，可以在一定持续时间内，统计认证失败的次数，在达到最大次数时，锁定账号一段时间，避免被暴力破解。
     */
    private Instant firstFailed;
    /**
     * 失败次数。
     * <p>
     * 简单的统计失败次数，失败次数的上限阈值一般从程序配置中获取。
     */
    private int failedCount = 0;
    /**
     * 锁定时间。
     * <p>
     * 第一次登录时，锁定时间不存在；触发锁定账户之后，只要不早于当前时间，则不属于锁定状态。
     * <p>
     * 这种逻辑可以避免锁定状态的循环检测。
     */
    private Instant locked;
    /**
     * 是否禁用。
     * <p>
     * 禁用账户比锁定账户更严重，因为这意味着，如果不手动启用，账户将始终无法使用。
     */
    private boolean disabled;
    /**
     * 最近玩过的剧本。
     */
    @OneToOne
    @ToString.Exclude
    private Script lastScript;

    /**
     * 身份证。
     * <p>
     * 用于未成年人防沉迷验证，同时控制账号数量。
     * <p>
     * 由于网页文字游戏很难去支撑一个面部识别系统，因此单纯的身份证绑定账号，并不能真正实现未成年人防沉迷功能。
     */
    @ManyToOne
    @ToString.Exclude
    private IdentityCard card;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 用户角色只用于控制访问路径（菜单权限、方法权限），所以我们在 api 模块下，不使用 spring-security 的权限控制
        // 若要开发粒度更细的权限控制（菜单权限、方法权限、数据权限），则需要考量 spring-security-acl 和 shiro 框架，哪种更容易实现需求
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked == null || Instant.now().isAfter(locked);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
