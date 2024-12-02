package com.carvalho.solution.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.domain.expense.ExpenseDTO;
import com.carvalho.solution.services.ExpenseService;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> saveOutput(@RequestBody @Valid ExpenseDTO outputDTO) {
        return ResponseEntity.ok(expenseService.saveOutput(outputDTO, UserContext.getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateOutput(@RequestBody @Valid ExpenseDTO outputDTO, @PathVariable Long id) {
        return ResponseEntity.ok(expenseService.updateOutput(outputDTO, id, UserContext.getTenantId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutput(@PathVariable Long id) {
        expenseService.deleteOutput(id, UserContext.getTenantId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> findOutputById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.findOutputById(id, UserContext.getTenantId()));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ExpenseDTO>> findAllByTenantId(Pageable pageable) {
        return ResponseEntity.ok(expenseService.findAllByTenantId(UserContext.getTenantId(), pageable));
    }

    @GetMapping("/interval-date")
    public ResponseEntity<List<ExpenseDTO>> findIntervalDate( 
    @RequestParam(required = true) 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)   
    LocalDateTime startDate,
    @RequestParam(required = true) 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime endDate) {   
        return ResponseEntity.ok(expenseService.findIntervalDate(startDate, endDate, UserContext.getTenantId()));
    }

}
