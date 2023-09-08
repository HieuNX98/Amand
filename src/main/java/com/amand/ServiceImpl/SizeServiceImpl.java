package com.amand.ServiceImpl;

import com.amand.constant.SystemConstant;
import com.amand.converter.SizeConverter;
import com.amand.dto.SizeDto;
import com.amand.entity.ProductEntity;
import com.amand.entity.SizeEntity;
import com.amand.form.SizeForm;
import com.amand.repository.ProductRepository;
import com.amand.repository.SizeRepository;
import com.amand.service.IProductService;
import com.amand.service.ISizeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SizeServiceImpl implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeConverter sizeConverter;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public SizeDto save(SizeForm sizeForm) {
        SizeEntity sizeEntity;
        if (sizeForm.getId() != null) {
            Optional<SizeEntity> oldSizeEntity = sizeRepository.findById(sizeForm.getId());
            sizeEntity = sizeConverter.toEntity(oldSizeEntity.get(), sizeForm);
        } else {
            sizeEntity = sizeConverter.toEntity(sizeForm);
            sizeEntity.setStatus(SystemConstant.ACTIVE_STATUS);
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
    public List<SizeDto> findAllByStatus(Integer status) {
        List<SizeDto> sizeDtos = new ArrayList<>();
        List<SizeEntity> sizeEntities = sizeRepository.findAllByStatus(status);
        for (SizeEntity sizeEntity : sizeEntities) {
            SizeDto sizeDto = sizeConverter.toDto(sizeEntity);
            sizeDtos.add(sizeDto);
        }
        return sizeDtos;
    }

    @Override
    public List<SizeDto> findAllByStatus(Pageable pageable, Integer status) {
        List<SizeEntity> sizeEntities = sizeRepository.findAllByStatus(pageable, status);
        List<SizeDto> sizeDtos = new ArrayList<>();
        for (SizeEntity sizeEntity : sizeEntities) {
            SizeDto sizeDto = sizeConverter.toDto(sizeEntity);
            sizeDtos.add(sizeDto);
        }
        return sizeDtos;
    }

    @Override
    public int getTotalItem(Integer status) {
        return sizeRepository.countByStatus(status);
    }

    @Override
    public SizeDto findOneById(Integer id) {
        Optional<SizeEntity> sizeEntity = sizeRepository.findById(id);
        if (sizeEntity.isEmpty()) return null;
        SizeDto sizeDto = sizeConverter.toDto(sizeEntity.get());
        return sizeDto;
    }

    @Override
    public String validateHide(List<Integer> ids) {
        String result = "";
        List<ProductEntity> productEntities = productRepository.findAllBySizeIds(ids);
        if (!CollectionUtils.isEmpty(productEntities)) {
            result = "Đang có sản phẩm thuộc danh sách kích thước bạn muốn xoá, bạn cần xoá sản phẩm thuộc danh sách kích thước" +
                    " này trước";
        }
        return result;
    }

    @Override
    @Transactional
    public void hide(List<Integer> ids) {
        sizeRepository.updateStatusByIds(ids);
    }
}
