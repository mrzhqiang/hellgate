package hellgate.common.model.script;

import hellgate.common.model.account.Account;
import lombok.Data;

import javax.persistence.Embeddable;
import java.time.Instant;

@Data
@Embeddable
public class ScriptHolder {

    // 时间
    private Instant instant;
    // 地点
    // 人物
    private Account account;
    // 事件

}
