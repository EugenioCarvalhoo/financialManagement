package com.carvalho.solution.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carvalho.solution.domain.tenant.TenantEntity;

public interface TenantRepository extends JpaRepository<TenantEntity, Long>  {
    
}
