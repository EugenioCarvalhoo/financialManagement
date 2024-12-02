package com.carvalho.solution.domain.receive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.carvalho.solution.domain.base.BaseEntity;
import com.carvalho.solution.domain.receiveitem.ReceiveItemEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "receive")
@Data
@EqualsAndHashCode(callSuper=false)
public class ReceiveEntity extends BaseEntity {

    @Column(name="description")
    private String description;

    @Column(name = "category", nullable = false )
    private String category;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Column(name = "tenant_id", nullable = false)
    private Long tenantId;

    @OneToMany(mappedBy = "receive", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceiveItemEntity> receiveItems = new ArrayList<>();
    
}
