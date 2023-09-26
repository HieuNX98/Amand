package com.amand.ServiceImpl;

import com.amand.converter.ProductBagConverter;
import com.amand.converter.ProductConverter;
import com.amand.dto.ProductBagDto;
import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductEntity;
import com.amand.repository.ProductBagRepository;
import com.amand.repository.ProductRepository;
import com.amand.service.IProductBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductBagServiceImpl implements IProductBagService {

    @Autowired
    private ProductBagRepository productBagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProductBagConverter productBagConverter;

    @Override
    public List<ProductBagDto> findAllByBagId(Integer bagId) {
        List<ProductBagDto> productBagDtos = new ArrayList<>();
        List<ProductEntity> productEntities = new ArrayList<>();
        List<ProductBagEntity> productBagEntities = productBagRepository.findAllByBagId(bagId);
        for (ProductBagEntity productBagEntity : productBagEntities) {
            ProductEntity productEntity = productRepository.findOneById(productBagEntity.getProduct().getId());
            ProductBagDto productBagDto = productBagConverter.toDto(productBagEntity);
            productBagDto.setProductDto(productConverter.toDto(productEntity));
            productBagDtos.add(productBagDto);
        }
        return productBagDtos;
    }
}
