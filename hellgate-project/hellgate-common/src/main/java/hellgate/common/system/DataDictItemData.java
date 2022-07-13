package hellgate.common.system;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DataDictItemData {

    @NotBlank
    private String label;
    @NotBlank
    private String value;
}
