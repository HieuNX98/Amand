package com.amand.ServiceImpl;

import com.amand.converter.BagConverter;
import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import com.amand.repository.BagRepository;
import com.amand.service.IBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BagServiceImpl implements IBagService {

    @Autowired
    private BagRepository bagRepository;

    @Autowired
    private BagConverter bagConverter;

    @Override
    public BagDto findByUserId(Integer userId) {
        BagEntity bagEntity = bagRepository.findByUserId(userId);
        return bagConverter.toDto(bagEntity);
    }
}
