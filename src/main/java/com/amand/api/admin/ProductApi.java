package com.amand.api.admin;

import com.amand.Utils.FileUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.dto.ProductDto;
import com.amand.form.ProductForm;
import com.amand.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Slf4j
public class ProductApi {

    @Autowired
    private IProductService productService;

    @PostMapping("/create-product")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductForm productForm) {
        Map<String, String> resultValidate = productService.validate(productForm, true);
        if (!CollectionUtils.isEmpty(resultValidate)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, resultValidate);
            return ResponseEntity.ok(response);
        } else {
            ProductDto result = productService.save(productForm);
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(result));
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/create-file-product")
    public ResponseEntity<?> createFileProduct(@RequestParam(name = "file") MultipartFile file) {
        String messageError = productService.validateFile(file);
        if (Strings.isBlank(messageError)) {
            try {
                List<ProductForm> productForms = FileUtils.readExcel(file);
                productService.save(productForms);
                ApiResponse apiResponse = new ApiResponse(SystemConstant.API_STATUS_OK);
                return ResponseEntity.ok(apiResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ApiResponse apiResponse = new ApiResponse(SystemConstant.API_STATUS_NG, messageError);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/edit-product")
    public ResponseEntity<?> updateProduct (@ModelAttribute ProductForm productForm) {
        Map<String, String> resultValidate = productService.validate(productForm, false);
        if (!CollectionUtils.isEmpty(resultValidate)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, resultValidate);
            return  ResponseEntity.ok(response);
        } else {
            ProductDto result = productService.save(productForm);
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(result));
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/hide-product")
    public ResponseEntity<?> hideProduct(@RequestBody List<Integer> ids) {
            productService.updateStatus(ids, SystemConstant.INACTIVE_STATUS);
            return ResponseEntity.ok().build();

    }

    @PutMapping("/restore-product")
    public ResponseEntity<?> restoreProduct(@RequestBody List<Integer> ids) {
        productService.updateStatus(ids, SystemConstant.ACTIVE_STATUS);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<?> deleteProduct(@RequestBody List<Integer> ids) {
        productService.deleteProduct(ids);
        return ResponseEntity.ok().build();
    }




}
