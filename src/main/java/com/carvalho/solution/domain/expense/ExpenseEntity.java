package com.carvalho.solution.domain.expense;

import com.carvalho.solution.domain.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
public class ExpenseEntity extends BaseEntity {
    
    @Column(nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

}
