package com.carvalho.solution.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carvalho.solution.domain.receive.ReceiveDTO;
import com.carvalho.solution.domain.receive.ReceiveEntity;
import com.carvalho.solution.domain.receiveitem.ReceiveItemDTO;
import com.carvalho.solution.domain.receiveitem.ReceiveItemEntity;
import com.carvalho.solution.exception.AppBusinessException;
import com.carvalho.solution.repositories.OrderCanceledRepository;
import com.carvalho.solution.repositories.ReceiveItemRepository;
import com.carvalho.solution.repositories.ReceiveRepository;
import com.carvalho.solution.util.TotalValueDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiveService {
    
    ModelMapper modelMapper;

    ReceiveRepository receiveRepository;

    ReceiveItemRepository receiveItemRepository;

    RefCodesService refCodesService;

    OrderCanceledRepository orderCanceledRepository;

    
    public Page<ReceiveDTO> findAllByTenantId(Long tenantId, Pageable pageable) {
        Page<ReceiveEntity> inputs = receiveRepository.findAllByTenantId(tenantId, pageable);
        return inputs.map(el -> {
            return modelMapper.map(el, ReceiveDTO.class);
        });
    }

    public ReceiveDTO saveInput(ReceiveDTO inputDTO, Long tenantId) {
        // List<KeyValue> filedList = new ArrayList<>();
        // filedList.add(KeyValue.of("paymentType", inputDTO.getPaymentType()));
        // filedList.add(KeyValue.of("inputCategory", inputDTO.getInputCategory()));
        
        // boolean isValid = refCodesService.validFieldsOptions(RefCodesFieldType.INPUT_ENTITY, filedList);
        
        // if (!isValid) {
        //     throw new AppBusinessException("Os dados informados estão invalídos.");
        // }

        inputDTO.setId(null);
        inputDTO.setStatus("HAVER");
        ReceiveEntity inputEntity = modelMapper.map(inputDTO, ReceiveEntity.class);
        inputEntity.setTenantId(tenantId);
        return modelMapper.map(receiveRepository.save(inputEntity), ReceiveDTO.class);
    }
    

    public ReceiveDTO updateInput(ReceiveDTO inputDTO, Long id, Long tenantId) {
        inputDTO.setStatus("HAVER");
        ReceiveEntity inputEntity = receiveRepository.findByIdWhitReceiveItems(id)
        .orElseThrow(() -> new AppBusinessException("Item não encontrada.")); 
        
        inputEntity.setDescription(inputDTO.getDescription());
        inputEntity.setCategory(inputDTO.getCategory());
        inputEntity.setAmount(inputDTO.getAmount());
        inputEntity.setEntryDate(inputDTO.getEntryDate());
        inputEntity.setOrderQuantity(inputDTO.getOrderQuantity());
        inputEntity.setStatus(inputDTO.getStatus());
        inputEntity.setId(id);
        inputEntity.setTenantId(tenantId);
        return modelMapper.map(receiveRepository.save(inputEntity), ReceiveDTO.class);
    }
    
    @Transactional
    public void deleteInput(Long id, Long tenantId) {
        ReceiveEntity entity = new ReceiveEntity();
        entity.setId(id);
        entity.setTenantId(tenantId);
        receiveRepository.delete(entity);
    }

    public ReceiveDTO findInputById(Long id, Long tenantId) {
        return modelMapper.map(receiveRepository.findByIdAndTenantId(id, tenantId), ReceiveDTO.class);
    }

    public List<TotalValueDTO> getTotalEntryDateByPaymentType(LocalDateTime startDate, LocalDateTime endDate, Long tenantId) {
        return modelMapper.map(
            receiveItemRepository.getTotalEntryDateByPaymentType( startDate, endDate, tenantId),
            new TypeToken<List<TotalValueDTO>>(){}.getType());
    }


    // ReceiveItem

    public List<ReceiveItemDTO> findAllReceiveItemByReceiveId(Long receiveId) {
        return modelMapper.map(receiveItemRepository.findByReceiveId(receiveId), new TypeToken<List<ReceiveItemDTO>>(){}.getType());
    }

    @Transactional
    public void updateReceiveItem(ReceiveItemDTO receiveItemDTO, Long id) {
        receiveItemDTO.setId(id);
        ReceiveEntity receiveEntity = receiveRepository.findByReceiveItemId(id).orElseThrow(() -> new AppBusinessException("Receita não encontrado."));
        ReceiveItemEntity receiveItemEntity = modelMapper.map(receiveItemDTO, ReceiveItemEntity.class);
        //BigDecimal newAmountreceive = receiveItemRepository.sumAmountByReceiveId(receiveEntity.getId());
        
        BigDecimal newAmountreceive = new BigDecimal(0);
        
        for (ReceiveItemEntity item: receiveEntity.getReceiveItems()) {
            if (item.getId().equals(id)) {
                item.setAmount(receiveItemEntity.getAmount());
                item.setPaymentType(receiveItemEntity.getPaymentType());
            }
            newAmountreceive = newAmountreceive.add(item.getAmount());
        }
        receiveEntity.setAmount(newAmountreceive);
        receiveRepository.save(receiveEntity);
    }

    @Transactional
    public void deleteReceiveItem( Long id) {
        ReceiveItemEntity receiveItemEntity = receiveItemRepository.findIdWhitReceive(id).orElseThrow(() -> new AppBusinessException("Receita não encontrado."));
        receiveItemRepository.delete(receiveItemEntity);
        BigDecimal newAmountreceive = receiveItemRepository.sumAmountByReceiveId(receiveItemEntity.getReceive().getId());
        receiveItemEntity.getReceive().setAmount(newAmountreceive);
        receiveRepository.save(receiveItemEntity.getReceive());
    }
}