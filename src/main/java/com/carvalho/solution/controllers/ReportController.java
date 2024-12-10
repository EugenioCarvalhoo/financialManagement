package com.carvalho.solution.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.domain.report.ReportItemDTO;
import com.carvalho.solution.services.ReportService;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @PostMapping
    public ReportItemDTO save( @Valid @RequestBody ReportItemDTO reportItemDTO) {
        return reportService.save(reportItemDTO, UserContext.getTenantId());
    }

    // @RequestMapping("/total")
    // public Object getTotal(
    //         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime startDate,
    //         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endDate
    //         ) {
    //     return balanceService.getTotal(startDate, endDate, UserContext.getTenantId());
    // }
}
