package com.carvalho.solution.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import com.carvalho.solution.domain.user.UserEntity;
import com.carvalho.solution.domain.user.UserRole;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserDetails findByLogin(String login);

    @Query("SELECT u FROM UserEntity u WHERE u.tenantId = :tenantId and u.role in :role")
    Page<UserEntity> findAllByTenantId(Long tenantId, List<UserRole> role, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE (u.email = :email or u.login = :login) and u.tenantId = :tenantId")
    Optional<UserEntity> findByLoginOrEmail(String login, String email, Long tenantId);

    @Query("SELECT u FROM UserEntity u WHERE u.login = :login and u.temporaryPassword = :temporaryPassword")
    Optional<UserEntity> findByLoginByTemporaryPassword(String login, String temporaryPassword);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id and u.tenantId = :tenantId")
    Optional<UserEntity> findByIdAndTenantId(Long id, Long tenantId);
}
