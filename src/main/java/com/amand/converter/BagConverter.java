package com.amand.converter;

import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import com.amand.entity.ProductEntity;
import com.amand.entity.UserEntity;
import com.amand.form.BagForm;
import org.springframework.stereotype.Component;

@Component
public class BagConverter {

    public BagDto toDto(BagEntity bagEntity) {
        BagDto bagDto = new BagDto();
        bagDto.setId(bagEntity.getId());
        bagDto.setTotalPrice(bagEntity.getTotalPrice());
        bagDto.setAmount(bagEntity.getAmount());
        bagDto.setUserId(bagEntity.getUser().getId());
        return bagDto;
    }

    public BagEntity toEntity(BagForm bagForm, UserEntity user, ProductEntity product) {
        BagEntity bagEntity = new BagEntity();
        bagEntity.setUser(user);
        bagEntity.setAmount(bagForm.getAmount());
        if (product.getSalePrice() == null) {
            bagEntity.setTotalPrice(product.getOldPrice() * bagForm.getAmount());
        } else {
            bagEntity.setTotalPrice(product.getSalePrice() * bagForm.getAmount());
        }
        return bagEntity;
    }

    public BagEntity toEntity(BagEntity bagEntity, ProductEntity product, BagForm bagForm) {
        if (product.getSalePrice() == null) {
            bagEntity.setTotalPrice(bagEntity.getTotalPrice() + product.getOldPrice() * bagForm.getAmount());
        } else {
            bagEntity.setTotalPrice(bagEntity.getTotalPrice() + product.getSalePrice() * bagForm.getAmount());
        }
        bagEntity.setAmount(bagEntity.getAmount() + bagForm.getAmount());
        return bagEntity;
    }
}
