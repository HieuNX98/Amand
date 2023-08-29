package com.amand.converter;

import com.amand.dto.SizeDto;
import com.amand.entity.SizeEntity;
import com.amand.form.SizeForm;
import org.springframework.stereotype.Component;

@Component
public class SizeConverter {

    public SizeEntity toEntity(SizeForm sizeForm) {
        SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setName(sizeForm.getName());
        return sizeEntity;
    }

    public SizeDto toDto(SizeEntity sizeEntity) {
        SizeDto sizeDto = new SizeDto();
            sizeDto.setId(sizeEntity.getId());
            sizeDto.setName(sizeEntity.getName());
        return sizeDto;
    }
}
