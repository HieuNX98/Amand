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

    List<ColorDto> findAllByStatus(Integer status);

    List<ColorDto> findAllByStatus(Pageable pageable, Integer status);

    int getTotalItem(Integer status);

    ColorDto findOneById(Integer id);

    void hide(List<Integer> ids);

    String validateHide(List<Integer> ids);

}
