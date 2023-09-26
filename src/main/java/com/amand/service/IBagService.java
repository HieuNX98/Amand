package com.amand.service;

import com.amand.dto.BagDto;
import com.amand.form.BagForm;

public interface IBagService {
    BagDto findByUserId(Integer userId);

    BagDto addBag(BagForm bagForm);
}
