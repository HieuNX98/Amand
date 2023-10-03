package com.amand.service;

import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import com.amand.form.BagForm;

public interface IBagService {
    BagDto findByUserId(Integer userId);

    Boolean addBag(BagForm bagForm);

    void delete(Integer id);

    BagDto findOneById(Integer id);
}
