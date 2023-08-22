package com.amand.converter;

import com.amand.dto.ProductDto;
import com.amand.entity.ColorEntity;
import com.amand.entity.ProductEntity;
import com.amand.entity.SizeEntity;
import com.amand.form.ProductForm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductConverter {
    public ProductEntity toEntity(ProductForm productForm) {
        ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productForm.getName());
            productEntity.setOldPrice(productForm.getOldPrice());
            productEntity.setSalePrice(productForm.getSalePrice());
            productEntity.setAmount(productForm.getAmount());
            productEntity.setSeason(productForm.getSeason());

            return productEntity;
    }

    public ProductDto toDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();

        productDto.setName(productEntity.getName());
            productDto.setOldPrice(productEntity.getOldPrice());
            productDto.setSalePrice(productEntity.getSalePrice());
            productDto.setAmount(productEntity.getAmount());
            productDto.setSeason(productEntity.getSeason());
            productDto.setImage1(productEntity.getImage1());
            productDto.setImage2(productEntity.getImage2());
            productDto.setImage3(productEntity.getImage3());
            productDto.setImage4(productEntity.getImage4());
        List<String> colorNames = new ArrayList<>();
        for (ColorEntity colorEntity : productEntity.getColors()) {
            String colorName = colorEntity.getName();
            colorNames.add(colorName);
            productDto.setColors(colorNames);
            }

        List<String> sizeNames = new ArrayList<>();
        for (SizeEntity sizeEntity : productEntity.getSizes()) {
                String sizeName = sizeEntity.getName();
                sizeNames.add(sizeName);
                productDto.setSizes(sizeNames);
            }

        return productDto;
    }
}
