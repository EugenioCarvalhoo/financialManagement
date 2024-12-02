package com.carvalho.solution.domain.ordercanceled;

import com.carvalho.solution.domain.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_canceled")
@Data
@EqualsAndHashCode(callSuper=false)
public class OrderCanceledEntity extends BaseEntity {

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "order_canceled_date")
    private LocalDateTime orderCanceledDate;

}
