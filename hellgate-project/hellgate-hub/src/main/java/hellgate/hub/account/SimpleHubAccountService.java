package hellgate.hub.account;

import com.github.mrzhqiang.helper.random.RandomStrings;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import hellgate.common.account.Account;
import hellgate.common.account.AccountRepository;
import hellgate.common.account.RegisterForm;
import hellgate.common.idcard.IdCard;
import hellgate.common.idcard.IdCardForm;
import hellgate.common.idcard.IdCardRepository;
import hellgate.hub.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SimpleHubAccountService implements HubAccountService {

    private static final CharMatcher NUMBER_MATCHER = CharMatcher.inRange('0', '9');

    private static final int UID_PREFIX_MIN_LENGTH = 3;
    private static final int UID_PREFIX_MAX_LENGTH = 5;
    private static final int UID_INFIX_MOD = 1000;
    private static final int UID_MIN_LENGTH = 7;
    private static final char UID_SUFFIX_PAD_CHAR = '0';

    private final AccountRepository repository;
    private final IdCardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties properties;

    public SimpleHubAccountService(AccountRepository repository,
                                   IdCardRepository cardRepository,
                                   PasswordEncoder passwordEncoder,
                                   SecurityProperties properties) {
        this.repository = repository;
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrUid) throws UsernameNotFoundException {
        // uid 是纯数字账号，username 首位必须是字母
        boolean matchesAllOfNumber = NUMBER_MATCHER.matchesAllOf(usernameOrUid);
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
    public Account findByUserDetails(UserDetails userDetails) {
        return repository.findByUsername(userDetails.getUsername())
                // 基本上不可能出现，除非数据库无法访问，或者已删除对应 username 的 account 表数据
                .orElseThrow(() -> new RuntimeException("当前会话无法找到对应的账户信息"));
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Account account = this.findByUserDetails(user);
        if (log.isDebugEnabled()) {
            log.debug("update password: {} to {}", account.getPassword(), newPassword);
        }
        account.setPassword(newPassword);
        repository.save(account);
        return User.withUserDetails(account).build();
    }

    @Override
    public String register(RegisterForm form) {
        String username = form.getUsername();
        if (repository.findByUsername(username).isPresent()) {
            return null;
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
        String bookmarkTemplate = properties.getBookmarkTemplate();
        return Strings.lenientFormat(bookmarkTemplate,
                form.getUsername(), form.getPassword(), Instant.now().getEpochSecond());
    }

    private String generateUid(String username) {
        // 参考：HashMap.hash(obj)
        int h;
        int code = (username == null) ? 0 : (h = username.hashCode()) ^ (h >>> 16);

        String uid = RandomStrings.ofNumber(UID_PREFIX_MIN_LENGTH, UID_PREFIX_MAX_LENGTH);
        // code 可能为负数，需要修正为绝对值，以免拼接出现 - 负数符号
        // 同时要保证 code 只有 3 位数字，防止最终 uid 过长
        uid = uid + Math.abs(code % UID_INFIX_MOD);
        // 保证最小长度为 7 位，不足 7 位尾部补零
        uid = Strings.padEnd(uid, UID_MIN_LENGTH, UID_SUFFIX_PAD_CHAR);
        return uid;
    }

    @Override
    public boolean binding(UserDetails userDetails, IdCardForm cardForm) {
        String number = cardForm.getNumber();
        IdCard card = cardRepository.findByNumber(number).orElseGet(IdCard::new);
        // 此身份证已经绑定了五个账号，不能再绑定
        List<Account> holders = card.getHolders();
        if (holders != null && holders.size() >= BIND_ID_CARD_MAX) {
            return false;
        }
        card.setNumber(number);
        card.setFullName(cardForm.getFullName());
        cardRepository.save(card);
        Account account = this.findByUserDetails(userDetails);
        account.setCard(card);
        repository.save(account);
        return true;
    }
}
