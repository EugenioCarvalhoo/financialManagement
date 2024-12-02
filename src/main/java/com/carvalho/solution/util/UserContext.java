package com.carvalho.solution.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.carvalho.solution.domain.user.UserEntity;

public class UserContext {
    
    public static final Long getTenantId() {
        var result = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return result.getTenantId();
    }

    public static final UserEntity getCurrentUser() {
        var result = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return result;
    }
}
