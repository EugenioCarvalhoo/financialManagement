package com.carvalho.solution.domain.receiveitem;

import java.math.BigDecimal;

import com.carvalho.solution.domain.base.BaseEntity;
import com.carvalho.solution.domain.receive.ReceiveEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "receive_item")
@Data
@EqualsAndHashCode(callSuper=false)
public class ReceiveItemEntity extends BaseEntity {

    @Column(name="payment_type")
    private String paymentType;

    @Column(name="amount")
    private BigDecimal amount;

    // @Column(name="receive_id")
    // private Long receiveId;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "receive_id")
    private ReceiveEntity receive;
    
}
