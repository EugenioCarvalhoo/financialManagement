package com.carvalho.solution.domain.receiveitem;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ReceiveItemDTO {

    private Long id;

    private String paymentType;

    private BigDecimal amount;

    private Long receiveId;
    
}
