package com.carvalho.solution.domain.report;

import java.util.ArrayList;
import java.util.List;

import com.carvalho.solution.domain.expense.ExpenseDTO;
import com.carvalho.solution.domain.ordercanceled.OrderCanceledDTO;
import com.carvalho.solution.domain.receive.ReceiveDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportItemDTO {
    
    ExpenseDTO expense;

    @NotNull
    List<ReceiveDTO> receives = new ArrayList<>();

    OrderCanceledDTO orderCanceled;
    
}
