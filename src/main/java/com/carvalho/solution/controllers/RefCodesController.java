package com.carvalho.solution.controllers;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.domain.refcode.RefCodesDTO;
import com.carvalho.solution.services.RefCodesService;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/refcodes")
@AllArgsConstructor
public class RefCodesController {
    
    RefCodesService refCodesService;

    @GetMapping("type/{type}/code/{code}")
    public ResponseEntity<List<RefCodesDTO>> findAllByTypeAnd(@PathVariable String type, @PathVariable String code) {
        return ResponseEntity.ok(refCodesService.findByTypeCode(type, code, UserContext.getTenantId()));
    }

    @PostMapping()
    public ResponseEntity<RefCodesDTO> save(@Valid @RequestBody RefCodesDTO dto) { 
        RefCodesDTO result = refCodesService.save(dto, UserContext.getTenantId());
        return ResponseEntity.ok().body(result);  
    }

    @PutMapping("{id}")
    public ResponseEntity<RefCodesDTO> update(@PathVariable String id, @Valid @RequestBody RefCodesDTO dto) {
        RefCodesDTO result = refCodesService.update(dto, UserContext.getTenantId());
        return ResponseEntity.ok().body(result); 
    }
    


}
