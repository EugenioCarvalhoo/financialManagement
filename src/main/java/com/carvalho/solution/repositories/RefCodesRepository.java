package com.carvalho.solution.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carvalho.solution.domain.refcode.RefCodesEntity;


public interface RefCodesRepository extends JpaRepository<RefCodesEntity, Long> {


    @Query("SELECT i FROM RefCodesEntity i WHERE i.id = :id")
    List<RefCodesEntity> findByIdAndTenantId(Long id);

    @Query("""
            SELECT i FROM RefCodesEntity i WHERE 
            upper(i.type) = upper(:type) AND upper(i.code) = upper(:code) 
            AND (i.tenantId IS NULL OR i.tenantId = :tenantId)
            """) 
    List<RefCodesEntity> findByTypeCode(String type, String code, Long tenantId);

}
