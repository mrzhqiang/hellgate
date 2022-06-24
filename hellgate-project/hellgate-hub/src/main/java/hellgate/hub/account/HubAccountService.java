package hellgate.hub.account;

import com.github.mrzhqiang.helper.random.RandomStrings;
import com.google.common.base.Strings;
import hellgate.common.account.AccountForm;
import hellgate.common.account.AccountService;
import hellgate.common.account.IdCardForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * 账号中心服务。
 */
public interface HubAccountService extends AccountService {

    /**
     * UID 参数名称。
     */
    String UID_KEY = "uid";
    /**
     * UID 前缀最小长度。
     */
    int UID_PREFIX_MIN_LENGTH = 3;
    /**
     * UID 前缀最大长度。
     */
    int UID_PREFIX_MAX_LENGTH = 5;
    /**
     * UID 中缀取模。
     */
    int UID_INFIX_MOD = 1000;
    /**
     * UID 最小长度。
     */
    int UID_MIN_LENGTH = 7;
    /**
     * UID 后缀填充字符。
     */
    char UID_SUFFIX_PAD_CHAR = '0';
    /**
     * 一个身份证号码被绑定的最大次数
     */
    int MAX_BINDING_ID_CARD_COUNT = 5;

    /**
     * 通过生成 UID 的方法。
     *
     * @param username 用户名。
     * @return uid 字符串。
     */
    default String generateUid(String username) {
        // 参考：HashMap.hash(obj) 由高 16 位组成 code 避免高频率的哈希冲撞
        int h;
        int code = (h = Objects.hashCode(username)) ^ (h >>> 16);
        // Math.floorMod 方法返回的数值符号由 y 参数符号决定，因此这里永远不会返回负数，符合预期
        int floorMod = Math.floorMod(code, UID_INFIX_MOD);

        // 生成位数为 min 及 max 之间的随机数字
        String uid = RandomStrings.ofNumber(UID_PREFIX_MIN_LENGTH, UID_PREFIX_MAX_LENGTH);
        uid = uid + floorMod;
        // 保证最小长度为 7 位，不足 7 位尾部补零
        uid = Strings.padEnd(uid, UID_MIN_LENGTH, UID_SUFFIX_PAD_CHAR);
        return uid;
    }

    /**
     * 通过用户名或 uid 加载用户详情。
     *
     * @param usernameOrUid 用户名/uid，用户名必须以字母开头，uid 则是纯数字。
     * @return 用户详情，通常是 {@link org.springframework.security.core.userdetails.User} 类。
     * @throws UsernameNotFoundException 当无法通过用户名找到账号时，抛出此异常，表示登录失败。
     */
    @Override
    UserDetails loadUserByUsername(String usernameOrUid) throws UsernameNotFoundException;

    /**
     * 注册账号。
     *
     * @param accountForm 前端传递过来的账号表单。
     * @return 返回 True 表示注册成功；否则注册失败。
     */
    boolean register(AccountForm accountForm);

    /**
     * 绑定身份证。
     *
     * @param userDetails 用户详情。
     * @param cardForm    身份证表单。
     * @return 返回 true 表示绑定成功；否则为绑定失败，通常是身份证已被绑定超过 5 次。
     */
    boolean binding(UserDetails userDetails, IdCardForm cardForm);
}
