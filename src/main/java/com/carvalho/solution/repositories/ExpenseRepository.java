package com.carvalho.solution.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carvalho.solution.domain.expense.ExpenseEntity;


public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    @Query("SELECT o FROM ExpenseEntity o WHERE o.tenantId = :tenantId AND o.id = :id")
    void deleteByIdAndTenantId(Long id, Long tenantId);

    @Query("SELECT o FROM ExpenseEntity o WHERE o.tenantId = :tenantId AND o.id = :id")
    ExpenseEntity findByIdAndTenantId(Long id, Long tenantId);

    Page<ExpenseEntity> findAllByTenantId(Long tenantId, Pageable pageable); 

    @Query(value = """
            SELECT
                category as type,
                SUM(amount) AS total
            FROM
                expense
            WHERE
                due_date BETWEEN :startDate AND :endDate and tenant_id = :tenantId
            GROUP BY
                type
            ORDER BY
                type
                """, nativeQuery = true)
    List<Map<String, Object>> getTotalDueDateByCategory(
     LocalDateTime startDate, LocalDateTime endDate, Long tenantId
        );

    @Query(value = """
            SELECT
                *
            FROM
                expense
            WHERE
                due_date BETWEEN :startDate AND :endDate and tenant_id = :tenantId
                """, nativeQuery = true)
    List<ExpenseEntity> findIntervalDate(LocalDateTime startDate, LocalDateTime endDate, Long tenantId);

}
