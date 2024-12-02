package com.carvalho.solution.controllers;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.services.BalanceService;
import com.carvalho.solution.util.UserContext;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/balance")
@AllArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/total")
    public Object getTotal(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endDate
            ) {
        return balanceService.getTotal(startDate, endDate, UserContext.getTenantId());
    }
}
