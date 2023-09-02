package com.amand.ServiceImpl;

import com.amand.converter.SizeConverter;
import com.amand.dto.SizeDto;
import com.amand.entity.SizeEntity;
import com.amand.form.SizeForm;
import com.amand.repository.SizeRepository;
import com.amand.service.ISizeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SizeServiceImpl implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeConverter sizeConverter;

    @Override
    public SizeDto save(SizeForm sizeForm) {
        SizeEntity sizeEntity;
        if (sizeForm.getId() != null) {
            Optional<SizeEntity> oldSizeEntity = sizeRepository.findById(sizeForm.getId());
            sizeEntity = sizeConverter.toEntity(oldSizeEntity.get(), sizeForm);
        } else {
            sizeEntity = sizeConverter.toEntity(sizeForm);
        }
        sizeEntity = sizeRepository.save(sizeEntity);
        return sizeConverter.toDto(sizeEntity);
    }

    @Override
    public Map<String, String> validate(SizeForm sizeForm, boolean isRegister) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNotBlank(sizeForm.getName())) {
            boolean isExistName = StringUtils.hasLength(sizeRepository.findOneNameByName(sizeForm.getName()));
            boolean isItsName = sizeForm.getName().equals(sizeRepository.findOneNameById(sizeForm.getId()));
            if (isRegister && isExistName) {
                result.put("MessageName", "Tên kích thước đã tồn tại");
            }

            if (!isRegister && isExistName && !isItsName) {
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

    @Override
    public List<SizeDto> findAll(Pageable pageable) {
        List<SizeEntity> sizeEntities = sizeRepository.findAll(pageable).getContent();
        List<SizeDto> sizeDtos = new ArrayList<>();
        for (SizeEntity sizeEntity : sizeEntities) {
            SizeDto sizeDto = sizeConverter.toDto(sizeEntity);
            sizeDtos.add(sizeDto);
        }
        return sizeDtos;
    }

    @Override
    public int getTotalItem() {
        return (int) sizeRepository.count();
    }

    @Override
    public SizeDto findOneById(Integer id) {
        Optional<SizeEntity> sizeEntity = sizeRepository.findById(id);
        if (sizeEntity.isEmpty()) return null;
        SizeDto sizeDto = sizeConverter.toDto(sizeEntity.get());
        return sizeDto;
    }
}
