package com.carvalho.solution.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.carvalho.solution.domain.tenant.TenantEntity;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Builder
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column()
    private String email;

    @Column()
    private String login;

    @Column(nullable = false)
    private String password;

    @Column( name = "temporary_password")
    private String temporaryPassword;

    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.OPERATIONAL;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;


    public static UserEntity createUserAdmin(RegisterDTO register, String encryptedPassword, TenantEntity tenant) {
        UserEntity entity = new UserEntity();
        entity.setLogin(register.getLogin());
        entity.setPassword(encryptedPassword);
        entity.setRole(UserRole.ADMIN);
        entity.setEmail(register.getLogin());
        entity.setFirstName(register.getFirstName());
        entity.setLastName(register.getLastName());
        entity.setTenantId(tenant.getId());
        return entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_OPERATIONAL"), new SimpleGrantedAuthority("ROLE_ASSISTANT_ADMIN"));
        if (this.role == UserRole.ASSISTANT_ADMIN) List.of( new SimpleGrantedAuthority("ROLE_ASSISTANT_ADMIN"), new SimpleGrantedAuthority("ROLE_OPERATIONAL"));
        return List.of(new SimpleGrantedAuthority("ROLE_OPERATIONAL"));
    }

   
    public String getLogin() {
        return login;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
