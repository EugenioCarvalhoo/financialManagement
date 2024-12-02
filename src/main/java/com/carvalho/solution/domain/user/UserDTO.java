package com.carvalho.solution.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String email;

    private String login;

    @NotBlank
    private String password;

    @NotNull
    private UserRole role = UserRole.OPERATIONAL;

    @NotNull
    private Boolean activeFlag = true;

}

