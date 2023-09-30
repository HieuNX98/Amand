package com.amand.ServiceImpl;

import com.amand.constant.SystemConstant;
import com.amand.converter.OrderConverter;
import com.amand.converter.ProductOrderConverter;
import com.amand.dto.OrderDto;
import com.amand.entity.BagEntity;
import com.amand.entity.OrderEntity;
import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductOrderEntity;
import com.amand.form.OrderForm;
import com.amand.repository.BagRepository;
import com.amand.repository.OrderRepository;
import com.amand.repository.ProductBagRepository;
import com.amand.repository.ProductOrderRepository;
import com.amand.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BagRepository bagRepository;

    @Autowired
    private ProductBagRepository productBagRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private ProductOrderConverter productOrderConverter;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Override
    @Transactional
    public OrderDto save(OrderForm orderForm) {
        OrderEntity orderEntity;
        ProductOrderEntity productOrderEntity;
        List<ProductOrderEntity> productOrderEntities = new ArrayList<>();
        BagEntity bagEntity = bagRepository.findOneById(orderForm.getBagId());
        orderEntity = orderConverter.toEntity(bagEntity, orderForm);
        orderEntity.setCodeOrder(generateRandomCodeOrder());
        orderEntity = orderRepository.save(orderEntity);
        List<ProductBagEntity> productBagEntities = productBagRepository.findAllByBagId(bagEntity.getId());
        for (ProductBagEntity productBagEntity : productBagEntities) {
            productOrderEntity = productOrderConverter.toEntity(productBagEntity, orderEntity);
            productOrderEntities.add(productOrderEntity);
        }
        productOrderRepository.saveAll(productOrderEntities);
        productBagRepository.deleteAllByBagId(bagEntity.getId());
        bagRepository.deleteById(bagEntity.getId());
        return orderConverter.toDto(orderEntity);
    }


    private String generateRandomCodeOrder() {
        long time = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(time);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(SystemConstant.CHARACTERS.length());
            char randomChar = SystemConstant.CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();

    }
}
