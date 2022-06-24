package hellgate.server.account;

import hellgate.common.account.Account;
import hellgate.common.account.AccountRepository;
import hellgate.common.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleAccountService implements AccountService {

    private final AccountRepository repository;

    public SimpleAccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("AccountService.usernameNotFound"));
    }

    @Override
    public Account findByUser(UserDetails user) {
        return repository.findByUsername(user.getUsername())
                // 基本上不可能出现，除非数据库无法访问，或者已删除对应 username 的 account 表数据
                .orElseThrow(() -> new RuntimeException("当前会话无法找到对应的账户信息"));
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Account account = this.findByUser(user);
        if (log.isDebugEnabled()) {
            log.debug("update password: {} to {}", account.getPassword(), newPassword);
        }
        account.setPassword(newPassword);
        repository.save(account);
        return User.withUserDetails(account).build();
    }
}
