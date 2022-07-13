package hellgate.common.system;

import lombok.Data;

import java.util.List;

@Data
public class SysSettingData {

    private String key;
    private String value;
    private List<SysSettingData> children;
}