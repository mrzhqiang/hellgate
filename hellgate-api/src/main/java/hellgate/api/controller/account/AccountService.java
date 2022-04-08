package hellgate.api.controller.account;

import com.github.mrzhqiang.helper.random.RandomStrings;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import hellgate.common.model.account.Account;
import hellgate.common.model.account.AccountForm;
import hellgate.common.model.account.AccountRepository;
import hellgate.common.model.account.IdentityCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // uid 是纯数字账号，username 首位必须是字母
        boolean matchesAllOfNumber = CharMatcher.inRange('0', '9').matchesAllOf(username);
        Optional<Account> optionalAccount;
        if (matchesAllOfNumber) {
            optionalAccount = repository.findByUid(username);
        } else {
            optionalAccount = repository.findByUsername(username);
        }
        return optionalAccount
                .map(User::withUserDetails)
                .map(User.UserBuilder::build)
                .orElseThrow(() -> new UsernameNotFoundException("AccountService.usernameNotFound"));
    }

    public boolean register(AccountForm form) {
        String username = form.getUsername();
        if (repository.findByUsername(username).isPresent()) {
            return false;
        }
        String uid = generateUid(username);
        while (repository.findByUid(uid).isPresent()) {
            // 随机生成 uid，避免重复
            uid = generateUid(username);
        }
        Account account = new Account();
        account.setUid(uid);
        account.setUsername(username);
        String encodePassword = passwordEncoder.encode(form.getPassword());
        account.setPassword(encodePassword);
        repository.save(account);
        if (log.isDebugEnabled()) {
            log.debug("create account: {} for register", account);
        }
        return true;
    }

    private String generateUid(String username) {
        // 参考：HashMap.hash(obj)
        int h;
        int code = (username == null) ? 0 : (h = username.hashCode()) ^ (h >>> 16);

        String uid = RandomStrings.ofNumber(3, 5);
        // code 可能为负数，需要修正为绝对值，以免拼接出现 - 负数符号
        // 同时要保证 code 只有 3 位数字，防止最终 uid 过长
        uid = uid + Math.abs(code % 1000);
        // 保证最小长度为 7 位，不足 7 位尾部补零
        uid = Strings.padEnd(uid, 7, '0');
        return uid;
    }

    public Account findByUserDetails(UserDetails userDetails) {
        return repository.findByUsername(userDetails.getUsername())
                // 基本上不可能出现，除非数据库无法访问，或者已删除对应 username 的 account 表数据
                .orElseThrow(() -> new RuntimeException("当前会话无法找到对应的账户信息"));
    }

    public void binding(UserDetails userDetails, IdentityCard card) {
        repository.findByUsername(userDetails.getUsername()).ifPresent(it -> {
            it.setCard(card);
            repository.save(it);
        });
    }
}
