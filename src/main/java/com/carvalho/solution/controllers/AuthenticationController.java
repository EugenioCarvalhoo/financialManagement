package com.carvalho.solution.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.domain.refcode.BalanceDateDTO;
import com.carvalho.solution.domain.refcode.RefCodesEntity;
import com.carvalho.solution.domain.tenant.TenantEntity;
import com.carvalho.solution.domain.user.ActiveUser;
import com.carvalho.solution.domain.user.AuthenticationDTO;
import com.carvalho.solution.domain.user.LoginResponseDTO;
import com.carvalho.solution.domain.user.RegisterDTO;
import com.carvalho.solution.domain.user.UserEntity;
import com.carvalho.solution.infra.security.TokenService;
import com.carvalho.solution.repositories.RefCodesRepository;
import com.carvalho.solution.repositories.TenantRepository;
import com.carvalho.solution.repositories.UserRepository;
import com.carvalho.solution.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthenticationController {

    AuthenticationManager authenticationManager;

    UserRepository repository;

    TokenService tokenService;

    UserService userService;

    TenantRepository tenantRepository;

    RefCodesRepository refCodesRepository;

    ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var user =  (UserEntity) auth.getPrincipal();
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        var tenant = tenantRepository.findById(((UserEntity) auth.getPrincipal()).getTenantId());

        var login = new LoginResponseDTO();
        login.setToken(token);
        login.setRole(((UserEntity) auth.getPrincipal()).getRole());
        login.setBussinessName(tenant.get().getBussinessName());
        login.setFirstName(user.getFirstName());
        login.setLastName(user.getLastName());

        return ResponseEntity.ok(login);
    }

    @PutMapping("active-user")
    public ResponseEntity<?> activeUser(@RequestBody @Valid ActiveUser dto) {        
        userService.activeUser(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws JsonProcessingException {
        if(this.repository.findByLogin(data.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        TenantEntity tenant = new TenantEntity();
        tenant.setName(data.getBussinessName());
        tenant.setDomain(data.getBussinessName());
        tenant.setBussinessName(data.getBussinessName());
        tenant = this.tenantRepository.save(tenant);

        UserEntity newUser = UserEntity.createUserAdmin(data, encryptedPassword, tenant);

        this.repository.save(newUser);
        

        BalanceDateDTO balanceDateDTO = new BalanceDateDTO();
        balanceDateDTO.setInitialDate("01");
        balanceDateDTO.setInitialDay("domingo");
        
        RefCodesEntity refCodesEntity = new RefCodesEntity();
        refCodesEntity.setActiveFlag(true);
        refCodesEntity.setCode("changeDate");
        refCodesEntity.setType("balance");
        refCodesEntity.setName(objectMapper.writeValueAsString(balanceDateDTO));
        refCodesEntity.setTenantId(tenant.getId());

        refCodesRepository.save(refCodesEntity);


        return ResponseEntity.ok().build();
    }
}
