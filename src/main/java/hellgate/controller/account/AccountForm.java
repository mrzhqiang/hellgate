package hellgate.controller.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 账户表单。
 * <p>
 * 注册、登录使用。
 */
@Data
public class AccountForm {

    /**
     * 用户名。
     * <p>
     * 只包含：数字、大小写字母。
     */
    @NotBlank
    @Size(min = 4, max = 15)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;
    /**
     * 密码。
     * <p>
     * 通常是明文密码，前端不做编码处理，由后端来决定是否编码。
     */
    @NotBlank
    @Size(min = 6, max = 15)
    private String password;
}
