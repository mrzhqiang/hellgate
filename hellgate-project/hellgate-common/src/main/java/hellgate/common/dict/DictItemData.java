package hellgate.common.dict;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DictItemData {

    @NotBlank
    private String label;
    @NotBlank
    private String value;
}
