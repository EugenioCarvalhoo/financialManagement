package com.carvalho.solution.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carvalho.solution.domain.tenant.TenantEntity;
import com.carvalho.solution.domain.user.ActiveUser;
import com.carvalho.solution.domain.user.ChangePassword;
import com.carvalho.solution.domain.user.UserAdminUpdateDTO;
import com.carvalho.solution.domain.user.UserBasicDTO;
import com.carvalho.solution.domain.user.UserEntity;
import com.carvalho.solution.domain.user.UserRole;
import com.carvalho.solution.domain.user.UserUpdate;
import com.carvalho.solution.exception.AppBusinessException;
import com.carvalho.solution.repositories.TenantRepository;
import com.carvalho.solution.repositories.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
   
    ModelMapper modelMapper;
    
    UserRepository userRepository;
    
    TenantRepository tenantRepository;
    
    public Page<UserBasicDTO> getUsers(UserEntity currrentUser, Pageable pageable) {
        List<UserRole> role = new ArrayList<>();
        role.add(UserRole.OPERATIONAL);
        if (currrentUser.getRole().equals(UserRole.ADMIN)) {
            role.add(UserRole.ASSISTANT_ADMIN);
        }
        Page<UserEntity> users = userRepository.findAllByTenantId(currrentUser.getTenantId(), role,pageable);
        return users.map(el -> {
            UserBasicDTO user = modelMapper.map(el, UserBasicDTO.class);
            return user;
        });
    }

    public UserAdminUpdateDTO updateUserAdmin(UserAdminUpdateDTO user, UserEntity currrentUser) {
        currrentUser.setFirstName(user.getFirstName());
        currrentUser.setLastName(user.getLastName());
        currrentUser.setLogin(user.getLogin());
        userRepository.save(currrentUser);
        return user;
    }

    @Transactional
    public UserUpdate updateUserProfile(@Valid UserUpdate user, Long id) {
        UserEntity currrentUser = userRepository.findById(id).orElseThrow(() -> new AppBusinessException("Usuário não encontrado."));
        TenantEntity tenant = null;
        if (currrentUser.getRole().equals(UserRole.ADMIN)) {
            tenant = tenantRepository.findById(currrentUser.getTenantId()).orElseThrow(() -> new AppBusinessException("Dados não correspondem."));
            tenant.setBussinessName(user.getBussinessName());
            tenantRepository.save(tenant);
            currrentUser.setEmail(user.getEmail());
        }
        currrentUser.setFirstName(user.getFirstName());
        currrentUser.setLastName(user.getLastName());
        currrentUser.setLogin(user.getLogin());
        var newUser = userRepository.save(currrentUser);
        var response = modelMapper.map(newUser, UserUpdate.class);
        if (tenant != null) {
            response.setBussinessName(tenant.getBussinessName());
        }
        return response;
    }

    public UserBasicDTO saveUser(@Valid UserBasicDTO user, Long tenantId) {
        user.setId(null);
        if (user.getLogin() == null) {
            throw new AppBusinessException("Login inválido.");
        }

        userRepository.findByLoginOrEmail(user.getLogin(), user.getEmail(), tenantId)
        .ifPresent(el -> {
           throw new AppBusinessException("Já existe um usuário cadastrado para os dados informados.");
        });
        
        UserEntity userEntity =  modelMapper.map(user, UserEntity.class);
        userEntity.setTenantId(tenantId);
        userEntity.setPassword(UUID.randomUUID().toString());
        var newUser = userRepository.save(userEntity);
        return modelMapper.map(newUser, UserBasicDTO.class);
    }

    public UserBasicDTO updateUser(UserBasicDTO user, Long id, Long tenantId) {
        if (user.getLogin() == null) {
            throw new AppBusinessException("Login inválido.");
        }

        UserEntity userEntity = userRepository.findByIdAndTenantId(id, tenantId).orElseThrow(
                    () -> new AppBusinessException("Já existe um usuario cadastrado para os dados informados.")
                );
        
        if (!StringUtils.isEmpty(user.getTemporaryPassword()) && !user.getTemporaryPassword().equals(userEntity.getPassword())) {
            userEntity.setPassword(UUID.randomUUID().toString());
        }

        userEntity.setLogin(user.getLogin());
        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setActiveFlag(user.getActiveFlag());

        var newUser = userRepository.save(userEntity);
        return modelMapper.map(newUser, UserBasicDTO.class);
    }

    public void changePassword(ChangePassword dto, Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppBusinessException("Usuário nao encontrado."));
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new AppBusinessException("As senhas informadas devem ser iguais.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getNewPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }


    public void activeUser(ActiveUser dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new AppBusinessException("As senhas informadas devem ser iguais.");
        }

        if (dto.getNewPassword().equals(dto.getOldPassword())) {
            throw new AppBusinessException("A nova senha deve ser diferente da senha temporária.");
        }

        UserEntity user = userRepository.findByLoginByTemporaryPassword(dto.getLogin(), dto.getOldPassword())
        .orElseThrow(() -> new AppBusinessException("Usuário nao encontrado."));

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getNewPassword());
        user.setPassword(encryptedPassword);
        user.setTemporaryPassword(null);
        userRepository.save(user);
    }

    public void delete(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new AppBusinessException("Usuário nao encontrado."));
        userRepository.delete(user);
    }
    
}
