package hellgate.hub.account;

import hellgate.common.account.AccountForm;
import hellgate.common.account.AccountService;
import hellgate.common.account.IdCardForm;
import org.springframework.security.core.userdetails.UserDetails;

public interface HubAccountService extends AccountService {

    /**
     * 一个身份证号码被绑定的最大次数
     */
    int BIND_ID_CARD_MAX = 5;
    /**
     * 注册账号。
     *
     * @param form 前端传递过来的账号表单。
     * @return 如果注册成功，返回一个重定向地址；否则返回 null 值。
     */
    String register(AccountForm form);

    /**
     * 绑定身份证。
     *
     * @param userDetails 用户详情。
     * @param cardForm    身份证表单。
     * @return 返回 true 表示绑定成功；否则为绑定失败，通常是身份证已被绑定超过 5 次。
     */
    boolean binding(UserDetails userDetails, IdCardForm cardForm);
}
