package com.carvalho.solution.services;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carvalho.solution.domain.expense.ExpenseDTO;
import com.carvalho.solution.domain.expense.ExpenseEntity;
import com.carvalho.solution.repositories.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ExpenseService {

    ModelMapper modelMapper;

    ExpenseRepository expenseRepository;

    public Page<ExpenseDTO> findAllByTenantId(Long tenantId, Pageable pageable) {
        Page<ExpenseEntity> inputs = expenseRepository.findAllByTenantId(tenantId, pageable);
        return inputs.map(el -> {
            return modelMapper.map(el, ExpenseDTO.class);
        });
    }

    public ExpenseDTO saveOutput(ExpenseDTO outputDTO, Long tenantId) {
        outputDTO.setId(null);
        ExpenseEntity outputEntity = modelMapper.map(outputDTO, ExpenseEntity.class);
        outputEntity.setTenantId(tenantId);
        return modelMapper.map(expenseRepository.save(outputEntity), ExpenseDTO.class);
    }

    public ExpenseDTO updateOutput(ExpenseDTO outputDTO, Long id, Long tenantId) {
        ExpenseEntity outputEntity = modelMapper.map(outputDTO, ExpenseEntity.class);
        outputEntity.setTenantId(tenantId);
        outputEntity.setId(id);
        return modelMapper.map(expenseRepository.save(outputEntity), ExpenseDTO.class);
    }

    public void deleteOutput(Long id, Long tenantId) {
        ExpenseEntity outputEntity = new ExpenseEntity();
        outputEntity.setId(id);
        outputEntity.setTenantId(tenantId);
        expenseRepository.delete(outputEntity);
    }

    public ExpenseDTO findOutputById(Long id, Long tenantId) {
        return modelMapper.map(expenseRepository.findByIdAndTenantId(id, tenantId), ExpenseDTO.class);
    }

    public List<ExpenseDTO> findIntervalDate(LocalDateTime startDate, LocalDateTime endDate, Long tenantId) {
        List<ExpenseEntity> expenses = expenseRepository.findIntervalDate(startDate, endDate, tenantId);
        return modelMapper.map(expenses, new TypeToken<List<ExpenseDTO>>() {}.getType());
    }

}
