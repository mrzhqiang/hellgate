package hellgate.common.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DictGroupData {

    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotNull
    private DictGroup.Type type;

    private List<DictItemData> items;
}
