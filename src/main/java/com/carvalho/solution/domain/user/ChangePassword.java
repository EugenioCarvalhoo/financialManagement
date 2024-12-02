package com.carvalho.solution.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePassword {
    
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String confirmNewPassword;

}
