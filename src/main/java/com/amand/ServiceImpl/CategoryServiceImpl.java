package com.amand.ServiceImpl;

import com.amand.converter.CategoryConverter;
import com.amand.dto.CategoryDto;
import com.amand.entity.CategoryEntity;
import com.amand.form.CategoryForm;
import com.amand.repository.CategoryRepository;
import com.amand.service.ICategoryService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    @Transactional
    public CategoryDto save(CategoryForm categoryForm) {
        CategoryEntity categoryEntity = categoryConverter.toEntity(categoryForm);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConverter.toDto(categoryEntity);
    }

    public Map<String, String> validate(CategoryForm categoryForm) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNotBlank(categoryForm.getName())){
            if (StringUtils.hasLength(categoryRepository.findOneByName(categoryForm.getName()))) {
                result.put("MessageName", "Tên thể loại sản phẩm đã tồn tại");
            }
        } else {
            result.put("MessageName", "Bạn không được để trống thông tin tên thể loại sản phẩm");
        }

        if (Strings.isNotBlank(categoryForm.getCode())) {
            if (StringUtils.hasLength(categoryRepository.findOneByCode(categoryForm.getCode()))) {
                result.put("MessageCode", "Tên mã code thể loại sản phẩm đã tồn tại");
            }
        } else {
            result.put("MessageCode", "Bạn không được để trống thông tin code thể loại sản phẩm");
        }
        return result;
    }

}
