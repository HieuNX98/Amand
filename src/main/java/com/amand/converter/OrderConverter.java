package com.amand.converter;

import com.amand.constant.SystemConstant;
import com.amand.dto.OrderDto;
import com.amand.entity.OrderEntity;
import com.amand.form.OrderForm;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public OrderEntity toEntity(OrderForm orderForm) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setFullName(orderForm.getFullName());
        orderEntity.setAddress(orderForm.getAddress());
        orderEntity.setEmail(orderForm.getEmail());
        orderEntity.setNote(orderForm.getNote());
        orderEntity.setPhone(orderForm.getPhone());
        orderEntity.setTransportFee(SystemConstant.TRANSPORT_FEE);
        orderEntity.setStatus(SystemConstant.INACTIVE_STATUS);
        return orderEntity;
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderEntity.getId());
        orderDto.setCodeOrder(orderEntity.getCodeOrder());
        orderDto.setFullName(orderEntity.getFullName());
        orderDto.setAddress(orderEntity.getAddress());
        orderDto.setEmail(orderEntity.getEmail());
        orderDto.setNote(orderEntity.getNote());
        orderDto.setPhone(orderEntity.getPhone());
        orderDto.setSubtotal(orderEntity.getTotalPrice());
        orderDto.setTransportFee(orderEntity.getTransportFee());
        orderDto.setTotalPrice(orderEntity.getTotalPrice());
        orderDto.setCreatedDate(orderEntity.getCreatedDate());
        return orderDto;
    }
}
