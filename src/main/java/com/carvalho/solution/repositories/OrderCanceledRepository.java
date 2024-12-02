package com.carvalho.solution.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carvalho.solution.domain.ordercanceled.OrderCanceledEntity;


public interface OrderCanceledRepository extends JpaRepository<OrderCanceledEntity, Long> {

    @Query("SELECT o FROM OrderCanceledEntity o WHERE o.tenantId = :tenantId AND o.id = :id")
    OrderCanceledEntity findByIdAndTenantId(Long id, Long tenantId);

    @Query(value = "select * from order_canceled o where cast(o.order_canceled_date as Date) = :orderCalceledDate and o.tenant_id = :tenantId ", nativeQuery= true)
    OrderCanceledEntity findByDateAndTenantId(LocalDate orderCalceledDate, Long tenantId);

    Page<OrderCanceledEntity> findAllByTenantId(Long tenantId, Pageable pageable); 

    @Query(value = """
            SELECT
                SUM(quantity) AS total
            FROM
                order_canceled
            WHERE
                order_canceled_date BETWEEN :startDate AND :endDate and tenant_id = :tenantId
                """, nativeQuery = true)
    BigDecimal getTotalCanceled(
     LocalDateTime startDate, LocalDateTime endDate, Long tenantId
        );

}
