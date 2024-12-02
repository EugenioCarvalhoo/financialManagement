package com.carvalho.solution.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.carvalho.solution.domain.receive.ReceiveEntity;

public interface ReceiveRepository extends JpaRepository<ReceiveEntity, Long> {

    @Modifying
    @Query("DELETE FROM ReceiveEntity i WHERE i.id = :id AND i.tenantId = :tenantId")
    void deleteByIdAndTenantId(Long id, Long tenantId);

    @Query("SELECT i FROM ReceiveEntity i WHERE i.id = :id AND i.tenantId = :tenantId")
    ReceiveEntity findByIdAndTenantId(Long id, Long tenantId);

    Page<ReceiveEntity> findAllByTenantId(Long tenantId, Pageable pageable);

    @Query(value = "SELECT r.* from receive r, receive_item ri where r.id = ri.receive_id and ri.id = :id", nativeQuery = true)
    Optional<ReceiveEntity> findByReceiveItemId(Long id);

    @Query(value = "SELECT r.* from receive r, receive_item ri where r.id = ri.receive_id and r.id = :id", nativeQuery = true)
    Optional<ReceiveEntity> findByIdWhitReceiveItems(Long id);

}
