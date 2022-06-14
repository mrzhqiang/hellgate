package hellgate.common.script;

import lombok.Data;

/**
 * 剧本数据。
 */
@Data
public class ScriptData {

    /**
     * 剧本标签的格式化模板。
     */
    public static final String LABEL_TEMPLATE = "%s[%s]";

    /**
     * 名称。
     */
    private String name;
    /**
     * 访问链接。
     */
    private String url;
}
