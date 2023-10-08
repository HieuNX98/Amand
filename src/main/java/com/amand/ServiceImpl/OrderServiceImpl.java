package com.amand.ServiceImpl;

import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.converter.OrderConverter;
import com.amand.converter.ProductOrderConverter;
import com.amand.dto.OrderDto;
import com.amand.entity.*;
import com.amand.form.OrderForm;
import com.amand.repository.*;
import com.amand.service.IOrderService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

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

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OrderEntity save(OrderForm orderForm) {
        OrderEntity orderEntity;
        ProductOrderEntity productOrderEntity;
        List<ProductOrderEntity> productOrderEntities = new ArrayList<>();
        if (orderForm.getBagId() == null) {
            orderEntity = orderConverter.toEntity(orderForm);
            UserEntity userEntity = userRepository.findOneById(SecurityUtils.getPrincipal().getUserId());
            orderEntity.setUser(userEntity);
            orderEntity.setTotalPrice(orderForm.getTotalPrice());
            orderEntity.setSubtotal(orderForm.getTotalPrice() - orderEntity.getTransportFee());
            orderEntity.setCodeOrder(generateRandomCodeOrder());
            productOrderEntity = productOrderConverter.toEntity(orderForm, orderEntity);
            ProductEntity productEntity = productRepository.findOneById(orderForm.getProductId());
            productOrderEntity.setProduct(productEntity);
            orderEntity = orderRepository.save(orderEntity);
            productOrderRepository.save(productOrderEntity);
        } else {
            Double subtotal = 0.0;
            BagEntity bagEntity = bagRepository.findOneById(orderForm.getBagId());
            orderEntity = orderConverter.toEntity(orderForm);
            UserEntity userEntity = userRepository.findOneById(SecurityUtils.getPrincipal().getUserId());
            orderEntity.setUser(userEntity);
            List<ProductBagEntity> productBagEntities = productBagRepository.findAllByBagId(bagEntity.getId());
            for (ProductBagEntity productBagEntity : productBagEntities) {
                ProductEntity productEntity = productRepository.findOneById(productBagEntity.getProduct().getId());
                if (productEntity.getSalePrice() == null) {
                    subtotal += productEntity.getOldPrice() * productBagEntity.getAmount();
                } else {
                    subtotal += productEntity.getSalePrice() * productBagEntity.getAmount();
                }
                productOrderEntity = productOrderConverter.toEntity(productBagEntity, orderEntity);
                productOrderEntities.add(productOrderEntity);
            }
            orderEntity.setSubtotal(subtotal);
            orderEntity.setTotalPrice(subtotal + orderEntity.getTransportFee());
            orderEntity.setCodeOrder(generateRandomCodeOrder());
            orderEntity = orderRepository.save(orderEntity);
            productOrderRepository.saveAll(productOrderEntities);
            productBagRepository.deleteAllByBagId(bagEntity.getId());
            bagRepository.deleteById(bagEntity.getId());
        }
        return orderEntity;
    }

    @Override
    public Map<String, String> validatePay(BindingResult result) {
        Map<String, String> responseMsg = new HashMap<>();
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                if (responseMsg.containsKey(error.getField())) {
                   String msg = error.getDefaultMessage() + "," + responseMsg.get(error.getField());
                   responseMsg.put(error.getField(), msg);
                } else {
                    responseMsg.put(error.getField(), error.getDefaultMessage());
                }

            }
        }
        return responseMsg;
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
