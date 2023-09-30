package com.amand.converter;

import com.amand.dto.OrderDto;
import com.amand.entity.BagEntity;
import com.amand.entity.OrderEntity;
import com.amand.form.OrderForm;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public OrderEntity toEntity(BagEntity bagEntity, OrderForm orderForm) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUser(bagEntity.getUser());
        orderEntity.setFullName(orderForm.getFullName());
        orderEntity.setAddress(orderForm.getAddress());
        orderEntity.setEmail(orderForm.getEmail());
        orderEntity.setNote(orderForm.getNote());
        orderEntity.setPhone(orderForm.getPhone());
        orderEntity.setSubtotal(bagEntity.getTotalPrice());
        orderEntity.setTotalPrice(bagEntity.getTotalPrice());
        return orderEntity;
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setCodeOrder(orderEntity.getCodeOrder());
        orderDto.setFullName(orderEntity.getFullName());
        orderDto.setAddress(orderEntity.getAddress());
        orderDto.setEmail(orderEntity.getEmail());
        orderDto.setNote(orderEntity.getNote());
        orderDto.setPhone(orderEntity.getPhone());
        orderDto.setSubtotal(orderEntity.getTotalPrice());
        orderDto.setTransportFee(orderEntity.getTransportFee());
        orderDto.setTotalPrice(orderEntity.getTotalPrice());
        return orderDto;
    }
}
