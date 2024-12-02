package com.carvalho.solution.domain.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdate {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String email;
    @NotBlank
    private String login;
    private String bussinessName;

}
