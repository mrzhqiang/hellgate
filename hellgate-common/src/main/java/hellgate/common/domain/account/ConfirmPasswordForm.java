package hellgate.common.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfirmPasswordForm extends AccountForm {

    @NotBlank
    @Size(min = 6, max = 15)
    @ToString.Exclude
    @JsonIgnore
    private String confirmPassword;
}
