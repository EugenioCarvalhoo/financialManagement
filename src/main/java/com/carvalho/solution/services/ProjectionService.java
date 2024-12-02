package com.carvalho.solution.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import com.carvalho.solution.domain.projection.ProjectionDTO;
import com.carvalho.solution.domain.projection.ProjectionEntity;
import com.carvalho.solution.repositories.ProjectionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectionService {

    ProjectionRepository projectionRepositoty;

    ModelMapper modelMapper;

    public List<ProjectionDTO> findAll(Long tenantId) {
        List<ProjectionEntity> projections = projectionRepositoty.findAllByTenantId(tenantId);
        return modelMapper.map(projections, new TypeToken<List<ProjectionDTO>>() {}.getType());
    }

    public ProjectionDTO findByIdAndTenantId(Long id, Long tenantId) {
        ProjectionEntity projections = projectionRepositoty.findByIdAndTenantId(id,tenantId);
        return modelMapper.map(projections, ProjectionDTO.class);
    }

    public ProjectionDTO save(ProjectionDTO projectionDTO, Long tenantId) {
        projectionDTO.setId(null);
        ProjectionEntity projection = modelMapper.map(projectionDTO, ProjectionEntity.class);
        projection.setTenantId(tenantId);
        return modelMapper.map(projectionRepositoty.save(projection), ProjectionDTO.class);
    }

    public ProjectionDTO update(ProjectionDTO projectionDTO, Long id, Long tenantId) {
        ProjectionEntity projection = modelMapper.map(projectionDTO, ProjectionEntity.class);
        projection.setId(id);
        projection.setTenantId(tenantId);
        return modelMapper.map(projectionRepositoty.save(projection), ProjectionDTO.class);
    }

    public void delete(Long id, Long tenantId) {
        projectionRepositoty.deleteByIdAndTenantId(id, tenantId);
    }

}
