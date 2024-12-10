package com.carvalho.solution.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carvalho.solution.domain.projection.ProjectionDTO;
import com.carvalho.solution.services.ProjectionService;
import com.carvalho.solution.util.UserContext;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("auth/projection")
@AllArgsConstructor
public class ProjectionController {

    ProjectionService projectionService;

    @GetMapping
    public ResponseEntity<List<ProjectionDTO>> findAll(){
        return ResponseEntity.ok(projectionService.findAll(UserContext.getTenantId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectionDTO> findByIdAndTenantId(@PathVariable Long id){
        return ResponseEntity.ok(projectionService.findByIdAndTenantId(id, UserContext.getTenantId()));
    }

    @PostMapping
    public ResponseEntity<ProjectionDTO> save(@RequestBody @Valid ProjectionDTO projectionDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectionService.save(projectionDTO, UserContext.getTenantId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectionDTO> update(@RequestBody @Valid ProjectionDTO projectionDTO, @PathVariable Long id){
        return ResponseEntity.ok(projectionService.update(projectionDTO, id, UserContext.getTenantId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        projectionService.delete(id, UserContext.getTenantId());
        return ResponseEntity.noContent().build();
    }

}
