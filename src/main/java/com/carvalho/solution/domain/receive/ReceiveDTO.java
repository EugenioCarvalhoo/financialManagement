package com.carvalho.solution.domain.receive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.carvalho.solution.domain.receiveitem.ReceiveItemDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceiveDTO {
    private Long id;

    private String description;

    @NotNull
    private String category;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime entryDate;

    @NotNull
    @Min(1)
    private Integer orderQuantity;

    private String status;

    private List<ReceiveItemDTO> receiveItems = new ArrayList<>();

}
