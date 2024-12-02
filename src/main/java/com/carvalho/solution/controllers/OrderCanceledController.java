package com.carvalho.solution.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.services.OrderCanceledService;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.carvalho.solution.domain.ordercanceled.OrderCanceledDTO;

@RestController
@RequestMapping("/order-canceled")
@AllArgsConstructor
public class OrderCanceledController {

    private final OrderCanceledService orderCanceledService;

    @PostMapping
    public ResponseEntity<OrderCanceledDTO> save(@RequestBody @Valid OrderCanceledDTO dto) {
        return ResponseEntity.ok(orderCanceledService.save(dto, UserContext.getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderCanceledDTO> update(@RequestBody @Valid OrderCanceledDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(orderCanceledService.update(dto, id, UserContext.getTenantId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderCanceledService.delete(id, UserContext.getTenantId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderCanceledDTO> findByIdAndTenantId(@PathVariable Long id) {
        return ResponseEntity.ok(orderCanceledService.findByIdAndTenantId(id, UserContext.getTenantId()));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<OrderCanceledDTO>> findAllByTenantId(Pageable pageable) {
        return ResponseEntity.ok(orderCanceledService.findAllByTenantId(UserContext.getTenantId(), pageable));
    }

}
