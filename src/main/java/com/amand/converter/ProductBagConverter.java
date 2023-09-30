package com.amand.converter;

import com.amand.dto.ProductBagDto;
import com.amand.entity.BagEntity;
import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductEntity;
import com.amand.form.BagForm;
import org.springframework.stereotype.Component;

@Component
public class ProductBagConverter {

    public ProductBagEntity toEntity(ProductEntity product, BagEntity bag, BagForm bagForm) {
      ProductBagEntity productBag = new ProductBagEntity();
        productBag.setProduct(product);
        productBag.setBag(bag);
        productBag.setColorName(bagForm.getColorName());
        productBag.setSizeName(bagForm.getSizeName());
        productBag.setAmount(bagForm.getAmount());
      return productBag;
    }

    public ProductBagDto toDto(ProductBagEntity productBagEntity) {
        ProductBagDto productBagDto = new ProductBagDto();
        productBagDto.setId(productBagEntity.getId());
        productBagDto.setSizeName(productBagEntity.getSizeName());
        productBagDto.setColorName(productBagEntity.getColorName());
        productBagDto.setAmount(productBagEntity.getAmount());
        return productBagDto;
    }
}
