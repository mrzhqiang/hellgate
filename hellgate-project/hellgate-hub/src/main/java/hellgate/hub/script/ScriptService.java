package hellgate.hub.script;

import hellgate.common.account.Account;

public interface ScriptService {

    /**
     * 根据账号加载相应的剧本数据。
     *
     * @param account 账号。
     * @return 剧本数据。
     */
    ScriptData loadBy(Account account);
}
