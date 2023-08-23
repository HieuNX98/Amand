package com.amand.api;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.form.CategoryForm;
import com.amand.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class CategoryApi {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryForm categoryForm) {
        Map<String, String> validateResult = categoryService.validate(categoryForm);
        if (!CollectionUtils.isEmpty(validateResult)) {
                ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateResult);
                return ResponseEntity.ok(response);
        } else {
                ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(categoryService.save(categoryForm)));
                return ResponseEntity.ok(response);
        }
    }
    @PutMapping("/category")
    public ResponseEntity<?> editCategory(@RequestBody CategoryForm categoryForm) {
        Map<String, String> validateRsult = categoryService.validate(categoryForm);
        if (!CollectionUtils.isEmpty(validateRsult)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateRsult);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(categoryService.save(categoryForm)));
            return ResponseEntity.ok(response);
        }
    }

}
