package com.carvalho.solution.domain.converter;

import com.carvalho.solution.domain.user.UserRole;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleStringConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        return (role != null) ? role.getRole() : null;
    }

    @Override
    public UserRole convertToEntityAttribute(String roleString) {
        if (roleString == null || roleString.isEmpty()) {
            return null;
        }
        for (UserRole role : UserRole.values()) {
            if (role.getRole().equalsIgnoreCase(roleString)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Valor inválido para UserRole: " + roleString);
    }
}
