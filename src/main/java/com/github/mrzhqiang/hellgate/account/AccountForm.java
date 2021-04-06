package com.github.mrzhqiang.hellgate.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AccountForm {

    @NotBlank
    @Size(min = 4, max = 15)
    private String username;
    @NotBlank
    @Size(min = 6, max = 15)
    private String password;

}
