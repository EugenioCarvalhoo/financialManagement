package com.carvalho.solution.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAdminUpdateDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String login;

}
