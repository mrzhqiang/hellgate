package hellgate.common.model.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class IdentityCardForm {

    /**
     * 身份证号码。
     * <p>
     * xxx xxx yyyy MM dd xx xx
     * <p>
     * 一共 18 位，前面六位是户籍编号，中间八位是出生年月日，后面四位是序列号，但同时包含性别等信息。
     */
    @NotBlank
    @Size(min = 18, max = 18)
    @Pattern(regexp = "^[0-9]{17}[0-9X]$", message = "{IdentityCardForm.number}")
    private String number;
    /**
     * 身份证姓名。
     */
    @NotBlank
    @Pattern(regexp = "[\\u4E00-\\u9FA5]+", message = "{IdentityCardForm.fullName}")
    private String fullName;
}
