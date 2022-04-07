package hellgate.api.controller.account;

import com.github.mrzhqiang.helper.random.RandomStrings;
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

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 通过用户名加载用户详情。
     * <p>
     * 主要是提供给 {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter 登录过滤器} 使用。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(User::withUserDetails)
                .map(User.UserBuilder::build)
                .orElseThrow(() -> new UsernameNotFoundException("AccountService.usernameNotFound"));
    }

    /**
     * 注册账户。
     *
     * @param form 注册时填写的表单。
     * @return true 表示注册成功；false 表示注册失败。基本上属于用户名已存在的问题。但我们一般不返回类似的提示信息，以免被恶意攻击。
     */
    public boolean register(AccountForm form) {
        String username = form.getUsername();
        if (repository.findByUsername(username).isPresent()) {
            return false;
        }
        String uid = generateUid(username);
        while (repository.findByUid(uid).isPresent()) {
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
        String uid = RandomStrings.ofNumber(2, 5);
        uid = uid + Math.abs(username.hashCode() % 1000);
        uid = Strings.padEnd(uid, 6, '0');
        return uid;
    }

    public Account findByUserDetails(UserDetails userDetails) {
        return repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("错误的会话"));
    }

    public void binding(UserDetails userDetails, IdentityCard card) {
        repository.findByUsername(userDetails.getUsername()).ifPresent(it -> {
            it.setCard(card);
            repository.save(it);
        });
    }
}
