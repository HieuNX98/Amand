package com.amand.converter;

import com.amand.dto.ColorDto;
import com.amand.entity.ColorEntity;
import com.amand.form.ColorForm;
import org.springframework.stereotype.Component;

@Component
public class ColorConverter {
    public ColorEntity toEntity(ColorForm colorForm) {
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setName(colorForm.getName());
        return colorEntity;
    }

    public ColorDto toDto(ColorEntity colorEntity) {
        ColorDto colorDto = new ColorDto();
        colorDto.setId(colorEntity.getId());
        colorDto.setName(colorEntity.getName());
        return colorDto;
    }
}
