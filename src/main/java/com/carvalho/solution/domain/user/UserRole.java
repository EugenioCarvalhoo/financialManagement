package com.carvalho.solution.domain.user;

public enum UserRole {
    ADMIN("admin"),
    OPERATIONAL("operational"),
    ASSISTANT_ADMIN("assistant_admin");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
