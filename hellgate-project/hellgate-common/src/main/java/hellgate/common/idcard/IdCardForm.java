package hellgate.common.idcard;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 身份证表单。
 * <p>
 * 填写身份证时使用。
 */
@Data
public class IdCardForm {

    /**
     * 身份证号码。
     * <p>
     * 适用于中国居民身份证，规则如下：
     * <p>
     * xxx xxx yyyy MM dd xx xx[X]
     * <p>
     * 一共 18 位，前面六位是户籍编号，中间八位是出生年、月、日，后面四位是序列号，但同时包含性别等信息。
     * <p>
     * 最后一位的大写字母 X 表示 10 这个数。
     */
    @NotBlank
    @Pattern(regexp = "^\\d{17}[\\dX]$", message = "{IdCardForm.number}")
    private String number;
    /**
     * 身份证姓名。
     * <p>
     * 目前只允许常用汉字，大概五千多字，使用统一编码字符的正则表达式进行校验。
     */
    @NotBlank
    @Pattern(regexp = "[\\u4E00-\\u9FA5]+", message = "{IdCardForm.fullName}")
    private String fullName;
}
