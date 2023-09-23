package com.amand.service;

import com.amand.dto.BagDto;

public interface IBagService {
    BagDto findByUserId(Integer userId);
}
