package com.amand.service;

import com.amand.dto.SizeDto;
import com.amand.form.SizeForm;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

public interface ISizeService {
    SizeDto save(SizeForm sizeForm);

    Map<String, String> validate(SizeForm sizeForm, boolean isRegister);

    List<SizeDto> findAllByStatus(Integer status);

    List<SizeDto> findAllByStatus(Pageable pageable, Integer status);

    int getTotalItem(Integer status);

    SizeDto findOneById(Integer id);
}
