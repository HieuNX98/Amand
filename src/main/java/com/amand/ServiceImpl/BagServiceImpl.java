package com.amand.ServiceImpl;

import com.amand.Utils.SecurityUtils;
import com.amand.converter.BagConverter;
import com.amand.converter.ProductBagConverter;
import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductEntity;
import com.amand.entity.UserEntity;
import com.amand.form.BagForm;
import com.amand.repository.BagRepository;
import com.amand.repository.ProductBagRepository;
import com.amand.repository.ProductRepository;
import com.amand.repository.UserRepository;
import com.amand.service.IBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BagServiceImpl implements IBagService {

    @Autowired
    private BagRepository bagRepository;

    @Autowired
    private BagConverter bagConverter;

    @Autowired
    private ProductBagRepository productBagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductBagConverter productBagConverter;

    @Override
    public BagDto findByUserId(Integer userId) {
        BagEntity bagEntity = bagRepository.findByUserId(userId);
        if (bagEntity == null) {
            return null;
        }
        BagDto bagDto = bagConverter.toDto(bagEntity);
        Double totalPrice = 0.0;
        List<ProductBagEntity> productBagEntities = productBagRepository.findAllByBagId(bagEntity.getId());
        for (ProductBagEntity productBagEntity : productBagEntities) {
            ProductEntity productEntity = productRepository.findOneById(productBagEntity.getProduct().getId());
            if (productEntity.getSalePrice() == null) {
                totalPrice += productEntity.getOldPrice() * productBagEntity.getAmount();
            } else {
                totalPrice += productEntity.getSalePrice() * productBagEntity.getAmount();
            }
            bagDto.setTotalPrice(totalPrice);
        }
        return bagDto;
    }


    @Override
    @Transactional
    public Boolean addBag(BagForm bagForm) {
        ProductEntity productEntity = productRepository.findOneByIdAndStatus(bagForm.getProductId());
        if (productEntity == null) {
            return false;
        }
        int userId = SecurityUtils.getPrincipal().getUserId();
        UserEntity userEntity = userRepository.findOneById(userId);
        int totalItem = bagRepository.countByUserId(userId);
        BagEntity bagEntity;
        ProductBagEntity productBagEntity;
        if (totalItem < 1) {
            bagEntity = bagConverter.toEntity(bagForm, userEntity);
            bagEntity = bagRepository.save(bagEntity);
            productBagEntity = productBagConverter.toEntity(productEntity, bagEntity, bagForm);
            productBagRepository.save(productBagEntity);
        } else {
            bagEntity = bagRepository.findByUserId(userId);
            bagEntity = bagConverter.toEntity(bagEntity, bagForm);
            bagEntity = bagRepository.save(bagEntity);
            ProductBagEntity oldProductBagEntity = productBagRepository.findAllByBagIdAndSizeNameAndColorName(bagEntity.getId(),
                                                                                                              bagForm.getSizeName(),
                                                                                                              bagForm.getColorName());
                if (oldProductBagEntity != null) {
                    oldProductBagEntity.setAmount(oldProductBagEntity.getAmount() + bagForm.getAmount());
                    productBagRepository.save(oldProductBagEntity);
                } else {
                    productBagEntity = productBagConverter.toEntity(productEntity, bagEntity, bagForm);
                    productBagRepository.save(productBagEntity);
                }
        }
        return true;
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ProductBagEntity productBag = productBagRepository.findOneById(id);
        BagEntity bagEntity = bagRepository.findOneById(productBag.getBag().getId());
        bagEntity = bagConverter.toEntity(productBag, bagEntity);
        bagRepository.save(bagEntity);
        if (bagEntity.getAmount() == 0) {
            bagRepository.deleteById(bagEntity.getId());
        }
        productBagRepository.deleteById(id);
    }

}
