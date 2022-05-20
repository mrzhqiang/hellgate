package hellgate.hub.script;

import hellgate.common.script.Script;
import lombok.Data;

import java.util.List;

@Data(staticConstructor = "of")
public class ScriptData {

    private final Script history;
    private final Script latest;
    private final List<Script> scripts;
}
