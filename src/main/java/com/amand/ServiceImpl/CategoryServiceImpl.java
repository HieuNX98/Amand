package com.amand.ServiceImpl;

import com.amand.constant.SystemConstant;
import com.amand.converter.CategoryConverter;
import com.amand.dto.CategoryDto;
import com.amand.entity.CategoryEntity;
import com.amand.entity.ProductEntity;
import com.amand.form.CategoryForm;
import com.amand.repository.CategoryRepository;
import com.amand.repository.ProductRepository;
import com.amand.service.ICategoryService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public CategoryDto save(CategoryForm categoryForm) {
        CategoryEntity categoryEntity;
        if (categoryForm.getId() != null) {
            Optional<CategoryEntity> oldCategory = categoryRepository.findById(categoryForm.getId());
            categoryEntity = categoryConverter.toEntity(oldCategory.get(), categoryForm);
        } else {
            categoryEntity = categoryConverter.toEntity(categoryForm);
            categoryEntity.setStatus(SystemConstant.ACTIVE_STATUS);
        }
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryConverter.toDto(categoryEntity);
    }

    public Map<String, String> validate(CategoryForm categoryForm, boolean isRegister) {
        Map<String, String> result = new HashMap<>();
        if (Strings.isNotBlank(categoryForm.getName())) {
            boolean isExistName = StringUtils.hasLength(categoryRepository.findOneNameByName(categoryForm.getName()));
            boolean isItsName = categoryForm.getName().equals(categoryRepository.findOneNameById(categoryForm.getId()));
            if (isRegister && isExistName) {
                result.put("MessageName", "Tên thể loại sản phẩm đã tồn tại");
            }

            if (!isRegister &&  isExistName && !isItsName){
                result.put("MessageName", "Tên thể loại sản phẩm đã tồn tại");
            }
        } else {
            result.put("MessageName", "Bạn không được để trống thông tin tên thể loại sản phẩm");
        }

        if (Strings.isNotBlank(categoryForm.getCode())) {
            boolean isExistCode = StringUtils.hasLength(categoryRepository.findOneCodeByCode(categoryForm.getCode())) ;
            boolean isItsCode = categoryForm.getCode().equals(categoryRepository.findOneCodeById(categoryForm.getId()));
            if (isRegister && isExistCode) {
                result.put("MessageCode", "Tên mã code thể loại sản phẩm đã tồn tại");
            }

            if (!isRegister && isExistCode && !isItsCode) {
                result.put("MessageCode", "Tên mã code thể loại sản phẩm đã tồn tại");
            }
        } else {
            result.put("MessageCode", "Bạn không được để trống thông tin code thể loại sản phẩm");
        }
        return result;
    }

    @Override
    public List<CategoryDto> findAllByStatus(Pageable pageable, Integer status) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByStatus(pageable, status);
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryDto categoryDto = categoryConverter.toDto(categoryEntity);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @Override
    public int getTotalItem(Integer status) {
        return categoryRepository.countByStatus(status);
    }

    @Override
    public List<CategoryDto> findAllByStatus(Integer status) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByStatus(status);
        for (CategoryEntity categoryEntity : categoryEntities) {
            CategoryDto categoryDto = categoryConverter.toDto(categoryEntity);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto findOneById(Integer id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if (categoryEntity.isEmpty()) return null;
        CategoryDto categoryDto = categoryConverter.toDto(categoryEntity.get());
        return categoryDto;
    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        categoryRepository.deleteAllById(ids);
    }

    @Override
    public String validateHide(List<Integer> ids) {
        String result ="";
        List<ProductEntity> productEntities = productRepository.findAllByCategoryIdAndStatus(SystemConstant.ACTIVE_STATUS ,ids);
        if (!CollectionUtils.isEmpty(productEntities)) {
            result = "Đang có sản phẩm thuộc danh sách thể loại bạn muốn ẩn, bạn cần ẩn sản phẩm thuộc danh sách thể loại này trước";
        }
        return result;

    }

    @Override
    @Transactional
    public void updateStatus(List<Integer> ids, Integer status) {
        categoryRepository.updateStatusByIds(status, ids);
    }

    @Override
    @Transactional
    public void deleteCategory(List<Integer> ids) {
        categoryRepository.deleteAllById(ids);
    }

    @Override
    public String validateDelete(List<Integer> ids) {
        String result = "";
        List<ProductEntity> productEntities = productRepository.findAllByCategoryIdAndStatus(SystemConstant.INACTIVE_STATUS, ids);
        if (!CollectionUtils.isEmpty(productEntities)) {
            result = "Đang có sản phẩm thuộc danh sách thể loại bạn muốn xoá, bạn cần xoá sản phẩm thuộc danh sách thể loại này trước";
        }
        return result;
    }

}
