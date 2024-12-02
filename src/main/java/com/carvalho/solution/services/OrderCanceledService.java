package com.carvalho.solution.services;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carvalho.solution.domain.ordercanceled.OrderCanceledDTO;
import com.carvalho.solution.domain.ordercanceled.OrderCanceledEntity;
import com.carvalho.solution.exception.AppBusinessException;
import com.carvalho.solution.repositories.OrderCanceledRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class OrderCanceledService {

    ModelMapper modelMapper;

    OrderCanceledRepository orderCanceledRepository;

    public Page<OrderCanceledDTO> findAllByTenantId(Long tenantId, Pageable pageable) {
        Page<OrderCanceledEntity> inputs = orderCanceledRepository.findAllByTenantId(tenantId, pageable);
        return inputs.map(el -> {
            return modelMapper.map(el, OrderCanceledDTO.class);
        });
    }

    public OrderCanceledDTO save(OrderCanceledDTO dto, Long tenantId) {
        dto.setId(null);
        OrderCanceledEntity existsEntitiy = orderCanceledRepository.findByDateAndTenantId(dto.getOrderCanceledDate().toLocalDate(), tenantId);
        
        if (existsEntitiy != null) {
            throw new AppBusinessException("Já existe um registro para a data informada");    
        }
        OrderCanceledEntity orderCanceledEntity = modelMapper.map(dto, OrderCanceledEntity.class);
        orderCanceledEntity.setTenantId(tenantId);
        return modelMapper.map(orderCanceledRepository.save(orderCanceledEntity), OrderCanceledDTO.class);
    }

    public OrderCanceledDTO update(OrderCanceledDTO dto, Long id, Long tenantId) {
        OrderCanceledEntity orderCanceledEntity = modelMapper.map(dto, OrderCanceledEntity.class);
        orderCanceledEntity.setTenantId(tenantId);
        orderCanceledEntity.setId(id);
        return modelMapper.map(orderCanceledRepository.save(orderCanceledEntity), OrderCanceledDTO.class);
    }

    public void delete(Long id, Long tenantId) {
        OrderCanceledEntity orderCanceledEntity = new OrderCanceledEntity();
        orderCanceledEntity.setId(id);
        orderCanceledEntity.setTenantId(tenantId);
        orderCanceledRepository.delete(orderCanceledEntity);
    }

    public OrderCanceledDTO findByIdAndTenantId(Long id, Long tenantId) {
        return modelMapper.map(orderCanceledRepository.findByIdAndTenantId(id, tenantId), OrderCanceledDTO.class);
    }

}
