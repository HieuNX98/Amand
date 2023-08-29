package com.amand.service;

import com.amand.dto.ColorDto;
import com.amand.dto.ProductDto;
import com.amand.form.ColorForm;

import java.util.List;
import java.util.Map;

public interface IColorService {
    ColorDto save(ColorForm colorForm);

    Map<String, String> validate(ColorForm colorForm);

    List<ColorDto> findAll();

}
