package com.amand.service;

import com.amand.dto.ColorDto;
import com.amand.dto.ProductDto;
import com.amand.form.ColorForm;
import com.amand.form.ProductForm;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IColorService {
    ColorDto save(ColorForm colorForm);

    Map<String, String> validate(ColorForm colorForm, boolean isRegister);

    List<ColorDto> findAll();

    List<ColorDto> findAll(Pageable pageable);

    int getTotalItem();

    ColorDto findOneById(Integer id);

}
