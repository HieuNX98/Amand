package com.amand.api.admin;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.dto.ProductDto;
import com.amand.form.ProductForm;
import com.amand.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Slf4j
public class ProductApi {

    @Autowired
    private IProductService productService;

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductForm productForm) {
        Map<String, String> resultValidate = productService.validate(productForm);
        if (!CollectionUtils.isEmpty(resultValidate)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, resultValidate);
            return ResponseEntity.ok(response);
        } else {
            ProductDto result = productService.save(productForm);
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(result));
            return ResponseEntity.ok(response);
        }
    }



}
