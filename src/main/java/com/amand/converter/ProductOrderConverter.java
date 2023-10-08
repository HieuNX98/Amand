package com.amand.converter;

import com.amand.entity.OrderEntity;
import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductOrderEntity;
import com.amand.form.OrderForm;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderConverter {

    public ProductOrderEntity toEntity(ProductBagEntity productBagEntity, OrderEntity orderEntity) {
        ProductOrderEntity productOrderEntity = new ProductOrderEntity();
        productOrderEntity.setColorName(productBagEntity.getColorName());
        productOrderEntity.setSizeName(productBagEntity.getSizeName());
        productOrderEntity.setAmount(productBagEntity.getAmount());
        productOrderEntity.setProduct(productBagEntity.getProduct());
        productOrderEntity.setOrder(orderEntity);
        return productOrderEntity;
    }

    public ProductOrderEntity toEntity(OrderForm orderForm, OrderEntity orderEntity) {
        ProductOrderEntity productOrderEntity = new ProductOrderEntity();
        productOrderEntity.setColorName(orderForm.getColorName());
        productOrderEntity.setSizeName(orderForm.getSizeName());
        productOrderEntity.setAmount(orderForm.getAmount());
        productOrderEntity.setOrder(orderEntity);
        return productOrderEntity;
    }
}
