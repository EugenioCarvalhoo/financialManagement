package com.carvalho.solution.util;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TotalValueDTO {
    
    private String type;
    private BigDecimal total;
}
