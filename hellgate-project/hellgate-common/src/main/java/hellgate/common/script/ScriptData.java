package hellgate.common.script;

import lombok.Data;

import java.util.List;

@Data
public class ScriptData {

    private String name;
    private String type;
    private String label;
    private String url;

    private List<RoleData> roles;
}
