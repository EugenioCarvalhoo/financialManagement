package com.carvalho.solution.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.domain.user.ActiveUser;
import com.carvalho.solution.domain.user.ChangePassword;
import com.carvalho.solution.domain.user.UserAdminUpdateDTO;
import com.carvalho.solution.domain.user.UserBasicDTO;
import com.carvalho.solution.domain.user.UserDTO;
import com.carvalho.solution.domain.user.UserEntity;
import com.carvalho.solution.domain.user.UserUpdate;
import com.carvalho.solution.services.UserService;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping("/page")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_ASSISTANT_ADMIN')")
    public ResponseEntity<Page<UserBasicDTO>> getUser(Pageable pageable) {     
        UserEntity currrentUser = UserContext.getCurrentUser();
        return ResponseEntity.ok().body(userService.getUsers( currrentUser, pageable));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_ASSISTANT_ADMIN')")
    public ResponseEntity<UserBasicDTO> saveUser(@RequestBody @Valid UserBasicDTO user) {        
        return ResponseEntity.ok().body(userService.saveUser(user, UserContext.getTenantId()));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_ASSISTANT_ADMIN')")
    public ResponseEntity<UserBasicDTO> updateUser(@RequestBody @Valid UserBasicDTO user, @PathVariable Long id) {     
        return ResponseEntity.ok().body(userService.updateUser(user, id,UserContext.getTenantId()));
    }

    @PutMapping("profile")
    public ResponseEntity<UserUpdate> updateUserProfile(@RequestBody @Valid UserUpdate user) {        
        return ResponseEntity.ok().body(userService.updateUserProfile(user, UserContext.getCurrentUser().getId()));
    }

    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePassword dto) {        
        userService.changePassword(dto, UserContext.getCurrentUser().getId());
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete( @PathVariable Long id) {
        userService.delete(id);     
        return ResponseEntity.noContent().build();
    }
    
}
