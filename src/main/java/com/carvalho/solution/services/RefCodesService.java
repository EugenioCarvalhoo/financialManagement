package com.carvalho.solution.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import com.carvalho.solution.domain.refcode.RefCodesDTO;
import com.carvalho.solution.domain.refcode.RefCodesEntity;
import com.carvalho.solution.domain.refcode.RefCodesFieldType;
import com.carvalho.solution.repositories.RefCodesRepository;
import com.carvalho.solution.util.KeyValue;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Service
public class RefCodesService {

    RefCodesRepository refCodesRepository;

    ModelMapper modelMapper;

    public List<RefCodesDTO> findAll() {
        return modelMapper.map(refCodesRepository.findAll(), new TypeToken<List<RefCodesDTO>>() {}.getType());
    }

    public List<RefCodesDTO> findByTypeCode(String type, String code, Long tenantId) {
        var result = refCodesRepository.findByTypeCode(type, code, tenantId);
        return modelMapper.map(result, new TypeToken<List<RefCodesDTO>>() {}.getType());
    }


    public List<RefCodesDTO> findAllByType( String type) {
        return modelMapper.map(refCodesRepository.findAll(), new TypeToken<List<RefCodesDTO>>() {}.getType());
    }

    public RefCodesDTO findById(Long id) {
        return modelMapper.map(refCodesRepository.findById(id), new TypeToken<RefCodesDTO>() {}.getType());
    }

    public RefCodesDTO save(RefCodesDTO refcodes, Long tenantId) {
        refcodes.setId(null);
        RefCodesEntity entity = modelMapper.map(refcodes, RefCodesEntity.class);
        if (refcodes.isWhitTenantId()) {
            entity.setTenantId(tenantId);
        }
        entity = refCodesRepository.save(entity);
        return modelMapper.map(entity, RefCodesDTO.class);
    }

    public void delete(Long id) {
        RefCodesEntity refCodesEntity = new RefCodesEntity();
        refCodesEntity.setId(id);
        refCodesRepository.delete(refCodesEntity);
    }

    public RefCodesDTO update(RefCodesDTO refcodes, Long id) {
        RefCodesEntity refCodesEntity = modelMapper.map(refcodes, RefCodesEntity.class);
        if (refcodes.isWhitTenantId()) {
            refCodesEntity.setTenantId(id );
        }

        refCodesEntity.setId(id);
        refCodesEntity =  refCodesRepository.save(refCodesEntity);

        return modelMapper.map(refCodesEntity, RefCodesDTO.class);    
    }


    public boolean validFieldsOptions(RefCodesFieldType refCodesFieldType, List<KeyValue> filedList) {
    
        var refCodesList = findAllByType(refCodesFieldType.getType());
        for( var el : refCodesList ) { 
            KeyValue remove = null;
            for (var field : filedList) {
                if (field.getKey().equalsIgnoreCase(el.getCode()) && field.getValue().equalsIgnoreCase(el.getName())) {
                    remove = field;
                }
            }
            if (remove != null) {
                filedList.remove(remove);
            }
            if (filedList.size() == 0) {
                break;
            }
        }

        return filedList.size() == 0;
    }

}
