package com.carvalho.solution.domain.ordercanceled;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCanceledDTO {
    private Long id;
    private BigDecimal quantity;
    @NotNull
    private LocalDateTime orderCanceledDate;
}
