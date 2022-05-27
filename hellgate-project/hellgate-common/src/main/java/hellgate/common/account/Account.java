package hellgate.common.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hellgate.common.audit.AuditableEntity;
import hellgate.common.script.Script;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.Collection;

/**
 * 账号。
 * <p>
 * 参考地狱之门的账号设计，即一个账号可以通行所有游戏。
 * <p>
 * 从开发角度来看，这实际上是类似单点登录的解决方案：
 * <p>
 * 1. 用户首先登录账号中心，获得指定游戏的跳转链接
 * <p>
 * 2. 随后携带账号认证信息访问跳转链接，此时指定游戏自动调用认证接口
 * <p>
 * 3. 认证成功则创建会话，认证失败则回退到账号中心
 * <p>
 * 目前有两种实现方案：
 * <p>
 * 1. 共享 Redis 会话，优点：会话管理方便，缺点：需要维护 Redis 中间件。
 * <p>
 * 2. JWT 无状态认证，优点：不需要额外的存储中间件，缺点：基本告别会话管理。
 * <p>
 * 鉴于以上优缺点，最终选择共享 Redis 会话方案，同时将 GameSession 设计为 GameContext 以避免混淆概念。
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class Account extends AuditableEntity implements UserDetails {

    /**
     * 用户名，创建时指定，唯一
     */
    @Column(updatable = false, unique = true, nullable = false)
    private String username;
    /**
     * 纯数字的唯一编号，创建时随机生成，可用于替代用户名进行登录
     */
    @Column(updatable = false, unique = true, nullable = false)
    private String uid;
    /**
     * 密码，创建时指定，加密存储
     * <p>
     * 密码绝对不允许明文存储，通过 Spring Security Crypto 的 PasswordEncoder 可以进行随机盐编码处理。
     */
    @JsonIgnore
    @ToString.Exclude
    @Column(nullable = false)
    private String password;
    /**
     * 指定时间区间（管理后台设定）内的首次登录失败时间戳
     * <p>
     * 记录首次失败时间，可以在一定持续时间内，统计认证失败的次数，在达到最大次数时，锁定账号一段时间，避免被暴力破解。
     */
    private Instant firstFailed;
    /**
     * 指定时间区间（管理后台设定）内的失败次数统计
     */
    private int failedCount = 0;
    /**
     * 锁定时间戳
     * <p>
     * 此时间戳如果不存在，或处于未来时间点，则说明账号被锁定。
     */
    private Instant locked;
    /**
     * 是否禁用
     * <p>
     * 禁用比锁定更严重，因为这意味着，如果不手动启用，账户将始终无法使用。
     */
    private boolean disabled;
    /**
     * 身份证。
     * <p>
     * 用于未成年人防沉迷验证，同时控制账号数量。
     * <p>
     * 由于网页文字游戏很难去支撑一个面部识别系统，因此单纯的身份证绑定账号，并不能真正实现未成年人防沉迷功能。
     */
    @ManyToOne
    @ToString.Exclude
    private IdCard card;
    /**
     * 历史剧本。
     * <p>
     * 只记录最近一次访问过的剧本。
     */
    @ManyToOne
    @ToString.Exclude
    private Script historyScript;

    /**
     * 获取授予权限列表。
     * <p>
     * 授予权限在 Spring Security 中指的是角色。
     * <p>
     * 角色可以用来控制访问 URL 路径，即菜单权限。
     * <p>
     * 角色也可以用来控制访问服务层方法，即按钮权限。
     * <p>
     * 更细粒度的权限也可以交由 Spring Security acl 框架来实现，它包含对数据权限的控制。
     * <p>
     * 当然，有经验的开发者也可以设计一套 AccessDecisionVoter 投票决策，它是上面所有内容的底层接口。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
