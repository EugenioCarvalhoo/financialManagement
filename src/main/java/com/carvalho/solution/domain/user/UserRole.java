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

    public static UserRole fromString(String role) {
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role não pode ser nulo ou vazio");
        }
        for (UserRole userRole : values()) {
            if (userRole.getRole().equalsIgnoreCase(role)) { // Ignore case se necessário
                return userRole;
            }
        }
        throw new IllegalArgumentException("Nenhum UserRole correspondente encontrado para: " + role);
    }
}
