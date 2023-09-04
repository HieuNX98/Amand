package com.amand.api.admin;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.form.CategoryForm;
import com.amand.service.ICategoryService;
import org.apache.logging.log4j.util.Strings;
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
        Map<String, String> validateResult = categoryService.validate(categoryForm, true);
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
        Map<String, String> validateRsult = categoryService.validate(categoryForm, false);
        if (!CollectionUtils.isEmpty(validateRsult)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateRsult);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(categoryService.save(categoryForm)));
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/category")
    public ResponseEntity<?> deleteCategory(@RequestBody List<Integer> ids) {
        String result = categoryService.validateDelete(ids);
        if (Strings.isNotBlank(result)) {
            return ResponseEntity.badRequest().body(result);
        }
        categoryService.delete(ids);
            return ResponseEntity.ok().build();
    }
}
