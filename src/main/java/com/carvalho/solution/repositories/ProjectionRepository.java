package com.carvalho.solution.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carvalho.solution.domain.projection.ProjectionEntity;

import java.util.List;


public interface ProjectionRepository extends JpaRepository<ProjectionEntity, Long> {

    @Query("SELECT p FROM ProjectionEntity p WHERE p.tenantId = :tenantId")
    List<ProjectionEntity> findAllByTenantId(Long tenantId);

    @Query("SELECT p FROM ProjectionEntity p WHERE p.id = :id AND p.tenantId = :tenantId")
    void deleteByIdAndTenantId(Long id, Long tenantId);

    @Query("SELECT p FROM ProjectionEntity p WHERE p.id = :id AND p.tenantId = :tenantId")
    ProjectionEntity findByIdAndTenantId(Long id, Long tenantId);
}
