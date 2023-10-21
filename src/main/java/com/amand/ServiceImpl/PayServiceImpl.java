package com.amand.ServiceImpl;

import com.amand.entity.PayEntity;
import com.amand.repository.PayRepository;
import com.amand.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private PayRepository payRepository;

    @Override
    @Transactional
    public void save(PayEntity payEntity) {
        payRepository.save(payEntity);
    }

}
