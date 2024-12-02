package com.carvalho.solution.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import com.carvalho.solution.repositories.ExpenseRepository;
import com.carvalho.solution.repositories.OrderCanceledRepository;
import com.carvalho.solution.repositories.ReceiveItemRepository;
import com.carvalho.solution.util.TotalValueDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BalanceService {

    private final ReceiveItemRepository receiveItemRepository;

    private final ExpenseRepository outputRepository;

    private OrderCanceledRepository orderCanceledRepository;

    private ModelMapper modelMapper;

    public Object getTotal(LocalDateTime startDate, LocalDateTime endDate,
            Long tenantId) {
        List<TotalValueDTO> totalEntryDate = modelMapper.map(
                receiveItemRepository.getTotalEntryDateByPaymentType(startDate, endDate, tenantId),
                new TypeToken<List<TotalValueDTO>>() {
                }.getType());

        List<TotalValueDTO> totalCategory = modelMapper.map(
                outputRepository.getTotalDueDateByCategory(startDate, endDate, tenantId),
                new TypeToken<List<TotalValueDTO>>() {
                }.getType());

        BigDecimal orderQuantity = receiveItemRepository.getTotalOrder(startDate, endDate, tenantId);
        BigDecimal orderCanceled = orderCanceledRepository.getTotalCanceled(startDate, endDate, tenantId);

        Map<String, Object> mapTotal = new HashMap<>();
        
        mapTotal.put("inputsByPaymentType", totalEntryDate);
        mapTotal.put("outputsByCategory", totalCategory);
        mapTotal.put("orderQuantity", orderQuantity);
        mapTotal.put("orderCanceled", orderCanceled);
        
        return mapTotal;

    }

}
