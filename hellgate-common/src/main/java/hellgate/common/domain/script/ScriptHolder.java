package hellgate.common.domain.script;

import hellgate.common.domain.account.Account;
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
