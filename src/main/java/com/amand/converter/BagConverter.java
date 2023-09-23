package com.amand.converter;

import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import org.springframework.stereotype.Component;

@Component
public class BagConverter {
    public BagDto toDto(BagEntity bagEntity) {
        BagDto bagDto = new BagDto();
        bagDto.setId(bagEntity.getId());
        bagDto.setProductName(bagEntity.getProductName());
        bagDto.setAmount(bagEntity.getAmount());
        bagDto.setColor(bagEntity.getColor());
        bagDto.setSize(bagEntity.getSize());
        bagDto.setProductImage(bagEntity.getProductImage());
        bagDto.setOldPrice(bagEntity.getOldPrice());
        return bagDto;
    }


}
