package com.amand.ServiceImpl;

import com.amand.converter.SizeConverter;
import com.amand.dto.SizeDto;
import com.amand.entity.SizeEntity;
import com.amand.form.SizeForm;
import com.amand.repository.SizeRepository;
import com.amand.service.ISizeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SizeServiceImpl implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeConverter sizeConverter;

    @Override
    public SizeDto save(SizeForm sizeForm) {
        SizeEntity sizeEntity = sizeConverter.toEntity(sizeForm);
        sizeEntity = sizeRepository.save(sizeEntity);
        return sizeConverter.toDto(sizeEntity);
    }

    @Override
    public Map<String, String> validate(SizeForm sizeForm) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNotBlank(sizeForm.getName())) {
            if (StringUtils.hasLength(sizeRepository.findOneNameByName(sizeForm.getName()))) {
                result.put("MessageName", "Tên kích thước đã tồn tại");
            }
        } else {
            result.put("MessageName", "Bạn không được để trống thông tin tên kích thước");
        }
        return result;
    }

    @Override
    public List<SizeDto> findAll() {
        List<SizeDto> sizeDtos = new ArrayList<>();
        List<SizeEntity> sizeEntities = sizeRepository.findAll();
        for (SizeEntity sizeEntity : sizeEntities) {
            SizeDto sizeDto = sizeConverter.toDto(sizeEntity);
            sizeDtos.add(sizeDto);
        }
        return sizeDtos;
    }
}
