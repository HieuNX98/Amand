package com.amand.converter;

import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductEntity;
import com.amand.entity.UserEntity;
import com.amand.form.BagForm;
import org.springframework.stereotype.Component;

@Component
public class BagConverter {

    public BagDto toDto(BagEntity bagEntity) {
        BagDto bagDto = new BagDto();
        bagDto.setId(bagEntity.getId());
        bagDto.setAmount(bagEntity.getAmount());
        bagDto.setUserId(bagEntity.getUser().getId());

        return bagDto;
    }

    public BagEntity toEntity(BagForm bagForm, UserEntity user) {
        BagEntity bagEntity = new BagEntity();
        bagEntity.setUser(user);
        bagEntity.setAmount(bagForm.getAmount());
        return bagEntity;
    }

    public BagEntity toEntity(BagEntity bagEntity, BagForm bagForm) {
        bagEntity.setAmount(bagEntity.getAmount() + bagForm.getAmount());
        return bagEntity;
    }

    public BagEntity toEntity(ProductBagEntity productBag, BagEntity bagEntity) {
        bagEntity.setAmount(bagEntity.getAmount() - productBag.getAmount());
        return bagEntity;
    }
}
