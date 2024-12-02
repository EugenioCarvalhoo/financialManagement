package com.carvalho.solution.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carvalho.solution.domain.receiveitem.ReceiveItemEntity;

public interface ReceiveItemRepository extends JpaRepository<ReceiveItemEntity, Long> {

    @Query("select r from ReceiveItemEntity r where r.receive.id = :receiveId")
    List<ReceiveItemEntity> findByReceiveId(Long receiveId);

    @Query(value = """
            select  sum(ri.amount) from receive_item ri, receive r where ri.receive_id = r.id and r.id = :receiveId
            """, nativeQuery = true)
    BigDecimal sumAmountByReceiveId(Long receiveId);

    @Query("select r from ReceiveItemEntity r inner join r.receive re where r.id = :id and re.id = r.receive.id")
    Optional<ReceiveItemEntity> findIdWhitReceive(Long id);

    @Query(value = """
             SELECT
            ri.payment_type as type,
                SUM(ri.amount) AS total
            FROM
                receive r, receive_item ri
            WHERE
                 r.entry_date BETWEEN :startDate AND :endDate and r.tenant_id = :tenantId and ri.receive_id = r.id
        GROUP BY
                type
            ORDER BY
                type
                """, nativeQuery = true)
    List<Map<String, Object>> getTotalEntryDateByPaymentType(
            LocalDateTime startDate, LocalDateTime endDate, Long tenantId);

    @Query(value = """
                    select  sum(r.order_quantity) from 
                    receive r where r.entry_date BETWEEN :startDate AND :endDate and r.tenant_id = :tenantId
                    """, nativeQuery = true)    
    BigDecimal getTotalOrder(LocalDateTime startDate, LocalDateTime endDate, Long tenantId);
}
