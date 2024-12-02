package com.carvalho.solution.domain.expense;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class ExpenseDTO {

    private Long id;
    
    private String description;

    @NotNull
    private String category;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private String status;

    @NotNull
    private String paymentType;

}
