package hellgate.hub.account;

import hellgate.common.account.Account;
import hellgate.common.account.AccountForm;
import hellgate.common.account.AccountRepository;
import hellgate.common.account.IdCard;
import hellgate.common.account.IdCardForm;
import hellgate.common.account.IdCardRepository;
import hellgate.common.util.Matchers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HubAccountJpaService implements HubAccountService {

    private final AccountRepository repository;
    private final IdCardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;

    public HubAccountJpaService(AccountRepository repository,
                                IdCardRepository cardRepository,
                                PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrUid) throws UsernameNotFoundException {
        // uid 是纯数字账号，username 首位必须是字母
        boolean matchesAllOfNumber = Matchers.PURE_NUMBER.matchesAllOf(usernameOrUid);
        Optional<Account> optionalAccount;
        if (matchesAllOfNumber) {
            optionalAccount = repository.findByUid(usernameOrUid);
        } else {
            optionalAccount = repository.findByUsername(usernameOrUid);
        }
        return optionalAccount
                .map(User::withUserDetails)
                .map(User.UserBuilder::build)
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

    @Override
    public boolean register(AccountForm accountForm) {
        String username = accountForm.getUsername();
        if (repository.findByUsername(username).isPresent()) {
            if (log.isDebugEnabled()) {
                log.debug("Username {} already exists for register", username);
            }
            return false;
        }
        String uid = this.generateUid(username);
        while (repository.findByUid(uid).isPresent()) {
            // 随机生成 uid，避免重复
            uid = this.generateUid(username);
        }
        Account account = new Account();
        account.setUid(uid);
        account.setUsername(username);
        String encodePassword = passwordEncoder.encode(accountForm.getPassword());
        account.setPassword(encodePassword);
        repository.save(account);
        if (log.isDebugEnabled()) {
            log.debug("Created account: {} for register", account);
        }
        return true;
    }

    @Override
    public boolean binding(UserDetails userDetails, IdCardForm cardForm) {
        String number = cardForm.getNumber();
        IdCard card = cardRepository.findByNumber(number).orElseGet(IdCard::new);
        // 此身份证已经绑定了五个账号，不能再绑定
        List<Account> holders = card.getHolders();
        if (holders != null && holders.size() >= MAX_BINDING_ID_CARD_COUNT) {
            return false;
        }
        card.setNumber(number);
        card.setFullName(cardForm.getFullName());
        cardRepository.save(card);
        Account account = this.findByUser(userDetails);
        account.setCard(card);
        repository.save(account);
        return true;
    }
}
