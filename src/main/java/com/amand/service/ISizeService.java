package com.amand.service;

import com.amand.dto.SizeDto;
import com.amand.form.SizeForm;

import java.util.List;
import java.util.Map;

public interface ISizeService {
    SizeDto save(SizeForm sizeForm);

    Map<String, String> validate(SizeForm sizeForm);

    List<SizeDto> findAll();
}
