package com.amand.ServiceImpl;

import com.amand.constant.SystemConstant;
import com.amand.converter.ColorConverter;
import com.amand.dto.ColorDto;
import com.amand.entity.ColorEntity;
import com.amand.entity.ProductEntity;
import com.amand.form.ColorForm;
import com.amand.repository.ColorRepository;
import com.amand.repository.ProductRepository;
import com.amand.service.IColorService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ColorServiceImpl implements IColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorConverter colorConverter;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public ColorDto save(ColorForm colorForm) {
        ColorEntity colorEntity;
        if (colorForm.getId() != null) {
            Optional<ColorEntity> oldColorEntity = colorRepository.findById(colorForm.getId());
            colorEntity = colorConverter.toEntity(oldColorEntity.get(), colorForm) ;
        } else {
            colorEntity = colorConverter.toEntity(colorForm);
            colorEntity.setStatus(SystemConstant.ACTIVE_STATUS);
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
    public List<ColorDto> findAllByStatus(Integer status) {
        List<ColorDto> colorDtos = new ArrayList<>();
        List<ColorEntity> colorEntities = colorRepository.findAllByStatus(status);
        for (ColorEntity colorEntity : colorEntities) {
            ColorDto colorDto = colorConverter.toDto(colorEntity);
            colorDtos.add(colorDto);
        }
        return colorDtos;
    }

    @Override
    public List<ColorDto> findAllByStatus(Pageable pageable, Integer status) {
        List<ColorDto> colorDtos = new ArrayList<>();
        List<ColorEntity> colorEntities = colorRepository.findAllByStatus(pageable, status);
        for (ColorEntity colorEntity : colorEntities) {
            ColorDto colorDto = colorConverter.toDto(colorEntity);
            colorDtos.add(colorDto);
        }
        return colorDtos;
    }

    @Override
    public int getTotalItem(Integer status) {
        return colorRepository.countByStatus(status);
    }

    @Override
    public ColorDto findOneById(Integer id) {
        Optional<ColorEntity> colorEntity = colorRepository.findById(id);
        if (colorEntity.isEmpty()) return null;
        ColorDto colorDto = colorConverter.toDto(colorEntity.get());
        return colorDto;
    }

    @Override
    @Transactional
    public void updateStatus(List<Integer> ids, Integer status) {
        colorRepository.updateStatusByIds(status, ids);

    }

    @Override
    public String validateHide(List<Integer> ids) {
        String result = "";
        if (ids == null || ids.isEmpty()) {
            result = "Bạn cần chọn màu bạn muốn xoá";
            return result;
        }
        List<ProductEntity> productEntities = productRepository.findAllByColorId(ids);
        if (!CollectionUtils.isEmpty(productEntities)) {
            result = "Đang có sản phẩm thuộc danh sách màu bạn muốn xoá, bạn cần xoá sản phẩm thuộc danh sách màu này trước";
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteColor(List<Integer> ids) {
        colorRepository.deleteAllById(ids);
    }


}
