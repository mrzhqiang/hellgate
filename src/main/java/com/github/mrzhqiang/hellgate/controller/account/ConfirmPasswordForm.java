package com.github.mrzhqiang.hellgate.controller.account;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConfirmPasswordForm extends AccountForm {

    @NotBlank
    @Size(min = 6, max = 15)
    private String confirmPassword;
}
