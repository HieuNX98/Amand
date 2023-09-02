package com.amand.ServiceImpl;

import com.amand.converter.ColorConverter;
import com.amand.dto.ColorDto;
import com.amand.dto.ProductDto;
import com.amand.entity.ColorEntity;
import com.amand.form.ColorForm;
import com.amand.form.ProductForm;
import com.amand.repository.ColorRepository;
import com.amand.service.IColorService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class ColorServiceImpl implements IColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorConverter colorConverter;

    @Override
    @Transactional
    public ColorDto save(ColorForm colorForm) {
        ColorEntity colorEntity;
        if (colorForm.getId() != null) {
            Optional<ColorEntity> oldColorEntity = colorRepository.findById(colorForm.getId());
            colorEntity = colorConverter.toEntity(oldColorEntity.get(), colorForm) ;
        } else {
            colorEntity = colorConverter.toEntity(colorForm);
        }
        colorEntity = colorRepository.save(colorEntity);
        return colorConverter.toDto(colorEntity);
    }

    @Override
    public Map<String, String> validate(ColorForm colorForm, boolean isRegister) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNotBlank(colorForm.getName())) {
            boolean isExistName = StringUtils.hasLength(colorRepository.findOneNameByName(colorForm.getName()));
            boolean isItsName = colorForm.getName().equals(colorRepository.findOneNameById(colorForm.getId()));
            if (isRegister && isExistName) {
                result.put("MessageName", "Tên màu đã tồn tại");
            }

            if (!isRegister && isExistName && !isItsName) {
                result.put("MessageName", "Tên màu đã tồn tại");
            }
        } else {
            result.put("MessageName", "Bạn không được để trống thông tin tên màu");
        }
        return result;
    }

    @Override
    public List<ColorDto> findAll() {
        List<ColorDto> colorDtos = new ArrayList<>();
        List<ColorEntity> colorEntities = colorRepository.findAll();
        for (ColorEntity colorEntity : colorEntities) {
            ColorDto colorDto = colorConverter.toDto(colorEntity);
            colorDtos.add(colorDto);
        }
        return colorDtos;
    }

    @Override
    public List<ColorDto> findAll(Pageable pageable) {
        List<ColorDto> colorDtos = new ArrayList<>();
        List<ColorEntity> colorEntities = colorRepository.findAll(pageable).getContent();
        for (ColorEntity colorEntity : colorEntities) {
            ColorDto colorDto = colorConverter.toDto(colorEntity);
            colorDtos.add(colorDto);
        }
        return colorDtos;
    }

    @Override
    public int getTotalItem() {
        return (int) colorRepository.count();
    }

    @Override
    public ColorDto findOneById(Integer id) {
        Optional<ColorEntity> colorEntity = colorRepository.findById(id);
        if (colorEntity.isEmpty()) return null;
        ColorDto colorDto = colorConverter.toDto(colorEntity.get());
        return colorDto;
    }

}
