package com.carvalho.solution.controllers;

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

import com.carvalho.solution.domain.receive.ReceiveDTO;
import com.carvalho.solution.domain.receiveitem.ReceiveItemDTO;
import com.carvalho.solution.services.ReceiveService;
import com.carvalho.solution.util.TotalValueDTO;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/receive")
@AllArgsConstructor
public class ReceiveController {

    private final ReceiveService receiveService;

    @PostMapping
    public ResponseEntity<ReceiveDTO> saveInput(@RequestBody @Valid ReceiveDTO inputDTO) {
        return ResponseEntity.ok(receiveService.saveInput(inputDTO, UserContext.getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiveDTO> updateInput(@RequestBody @Valid ReceiveDTO inputDTO, @PathVariable Long id) {
        return ResponseEntity.ok(receiveService.updateInput(inputDTO, id, UserContext.getTenantId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInput(@PathVariable Long id) {
        receiveService.deleteInput(id, UserContext.getTenantId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiveDTO> findInputById(@PathVariable Long id) {
        return ResponseEntity.ok(receiveService.findInputById(id, UserContext.getTenantId()));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ReceiveDTO>> findAllByTenantId(Pageable pageable) {
        return ResponseEntity.ok(receiveService.findAllByTenantId(UserContext.getTenantId(), pageable));
    }

    @GetMapping("/total-entrydate-paymenttype")
    public List<TotalValueDTO> getTotalEntryDateByPaymentType(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endDate) {
        return receiveService.getTotalEntryDateByPaymentType(startDate, endDate, UserContext.getTenantId());
    }

    // ReceiveItem

    @GetMapping("/items-by-receive-id")
    public ResponseEntity<List<ReceiveItemDTO>> findAllReceiveItemByReceiveId(@RequestParam Long receiveId) {
        return ResponseEntity.ok(receiveService.findAllReceiveItemByReceiveId(receiveId));
    }

    @PutMapping("/item/id/{id}")
    public ResponseEntity<?> updateReceiveItem(@RequestBody @Valid ReceiveItemDTO receiveItemDTO, @PathVariable Long id) {
        receiveService.updateReceiveItem(receiveItemDTO, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/item/id/{id}")
    public ResponseEntity<?> deleteReceiveItem(@PathVariable Long id) {
        receiveService.deleteReceiveItem(id);
        return ResponseEntity.noContent().build();
    }

}