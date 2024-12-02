package com.carvalho.solution.domain.user;

import lombok.Data;

@Data
public class UserBasicDTO {

    private Long id;

    private String firstName;
private String lastName;

    private String email;

    private String temporaryPassword;

    private String login;

    private Boolean activeFlag = true;
    
}
