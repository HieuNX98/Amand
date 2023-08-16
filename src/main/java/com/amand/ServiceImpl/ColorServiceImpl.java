package com.amand.ServiceImpl;

import com.amand.converter.ColorConverter;
import com.amand.dto.ColorDto;
import com.amand.entity.ColorEntity;
import com.amand.form.ColorForm;
import com.amand.repository.ColorRepository;
import com.amand.service.IColorService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class ColorServiceImpl implements IColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorConverter colorConverter;

    @Override
    @Transactional
    public ColorDto save(ColorForm colorForm) {
        ColorEntity colorEntity = colorConverter.toEntity(colorForm);
        colorEntity = colorRepository.save(colorEntity);
        return colorConverter.toDto(colorEntity);
    }

    @Override
    public Map<String, String> validate(ColorForm colorForm) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNotBlank(colorForm.getName())) {
            if (StringUtils.hasLength(colorRepository.findOneByName(colorForm.getName()))) {
                result.put("MessageName", "Tên màu đã tồn tại");
            }
        } else {
            result.put("MessageName", "Bạn không được để trống thông tin tên màu");
        }
        return result;
    }
}
