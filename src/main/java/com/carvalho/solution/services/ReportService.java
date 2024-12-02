package com.carvalho.solution.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carvalho.solution.domain.expense.ExpenseDTO;
import com.carvalho.solution.domain.expense.ExpenseEntity;
import com.carvalho.solution.domain.ordercanceled.OrderCanceledEntity;
import com.carvalho.solution.domain.receive.ReceiveEntity;
import com.carvalho.solution.domain.report.ReportItemDTO;
import com.carvalho.solution.exception.AppBusinessException;
import com.carvalho.solution.repositories.ExpenseRepository;
import com.carvalho.solution.repositories.OrderCanceledRepository;
import com.carvalho.solution.repositories.ReceiveRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {
    
    ExpenseRepository expenseRepository;

    ReceiveRepository receiveRepository;

    OrderCanceledRepository orderCanceledRepository;

    ModelMapper modelMapper;

    @Transactional
    public ReportItemDTO save(ReportItemDTO reportItemDTO, Long tenantId) {
        if (reportItemDTO == null || reportItemDTO.getReceives().isEmpty()) {
            throw new AppBusinessException("Dados inválidos");
        }
        
        ExpenseDTO expense = reportItemDTO.getExpense();

        if (expense != null) {
            expense.setId(null);
            ExpenseEntity expenseEntity = modelMapper.map(expense, ExpenseEntity.class);
            expense.setStatus("PAGO");
            expenseEntity.setTenantId(tenantId);
            expenseRepository.save(expenseEntity);
        }

        List<ReceiveEntity> reveives = reportItemDTO.getReceives()
        .stream().map(dto -> {
            ReceiveEntity entity = modelMapper.map(dto, ReceiveEntity.class);
            entity.setStatus("CRIADO");
            entity.setTenantId(tenantId);
            entity.getReceiveItems().forEach(item -> item.setReceive(entity));
            return entity;
        }).collect(Collectors.toList());

        if (reportItemDTO.getOrderCanceled() != null) {
            OrderCanceledEntity orderCanceledEntity = modelMapper.map(reportItemDTO.getOrderCanceled(), OrderCanceledEntity.class);
            orderCanceledEntity.setTenantId(tenantId);
            orderCanceledRepository.save(orderCanceledEntity);
        }


        receiveRepository.saveAll(reveives);

        return reportItemDTO;
    }
}
