package com.carvalho.solution.domain.user;

import lombok.Data;

@Data
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String bussinessName;
    private String login;
    private String password;
    private UserRole role;

}
