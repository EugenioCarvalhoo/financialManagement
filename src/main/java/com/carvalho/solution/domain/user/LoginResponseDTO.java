package com.carvalho.solution.domain.user;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String firstName;
    private String lastName;
    private String token;
    private UserRole role;
    private String bussinessName;
}
