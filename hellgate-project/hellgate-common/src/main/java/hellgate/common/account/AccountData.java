package hellgate.common.account;

import hellgate.common.idcard.IdCardData;
import hellgate.common.script.ScriptData;
import lombok.Data;

/**
 * 账号数据。
 * <p>
 * 通常用于视图层展示。
 */
@Data
public class AccountData {

    private String username;
    private String uid;
    private IdCardData card;
    private ScriptData historyScript;
}
