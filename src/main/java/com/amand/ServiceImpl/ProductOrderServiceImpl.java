package com.amand.ServiceImpl;

import com.amand.constant.SystemConstant;
import com.amand.converter.ProductOrderConverter;
import com.amand.dto.ProductDto;
import com.amand.dto.ProductOrderDto;
import com.amand.entity.ProductEntity;
import com.amand.entity.ProductOrderEntity;
import com.amand.repository.ProductOrderRepository;
import com.amand.repository.ProductRepository;
import com.amand.service.IProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductOrderServiceImpl implements IProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderConverter productOrderConverter;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductOrderDto> findAllByOrderId(Integer orderId, Integer status) {
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        double price = 0.0;
        List<ProductOrderEntity> productOrderEntities = productOrderRepository.findAllByOrderId(orderId, status);
        for (ProductOrderEntity productOrderEntity : productOrderEntities) {
            ProductOrderDto productOrderDto = productOrderConverter.toDto(productOrderEntity);
            if (productOrderEntity.getProduct().getSalePrice() == null) {
                price = productOrderEntity.getProduct().getOldPrice() * productOrderEntity.getAmount();
                productOrderDto.setTotalPrice(price);
            } else {
                price = productOrderEntity.getProduct().getSalePrice() * productOrderEntity.getAmount();
                productOrderDto.setTotalPrice(price);
            }

            productOrderDtos.add(productOrderDto);
        }
        return productOrderDtos;
    }

    @Override
    public List<ProductOrderEntity> findByOrderId(Integer orderId, Integer status) {
        List<ProductOrderEntity> productOrderEntities = productOrderRepository.findAllByOrderId(orderId, status);
        return productOrderEntities;
    }

    @Override
    public List<ProductOrderDto> findAllByOrderId(Integer orderId) {
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        List<ProductOrderEntity> productOrderEntities = productOrderRepository.findAllByOrderId(orderId, SystemConstant.INACTIVE_STATUS);
        for (ProductOrderEntity productOrderEntity : productOrderEntities) {
            ProductOrderDto productOrderDto = productOrderConverter.toDto(productOrderEntity);
            ProductEntity productEntity = productRepository.findOneById(productOrderEntity.getProduct().getId());
            productOrderDto.setOldPrice(productEntity.getOldPrice());
            productOrderDto.setSalePrice(productEntity.getSalePrice());
            productOrderDto.setImage1(productEntity.getImage1());
            productOrderDtos.add(productOrderDto);
        }
        /*List<ProductOrderDto> productOrderDtos = productOrderRepository.getProductDto(orderId);*/
        return productOrderDtos;
    }

}
