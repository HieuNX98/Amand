package com.amand.api;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.form.SizeForm;
import com.amand.service.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class SizeApi {

    @Autowired
    private ISizeService sizeService;

    @PostMapping("/size")
    public ResponseEntity<?> createColor(@RequestBody SizeForm sizeForm) {
        Map<String, String> resultValidate = sizeService.validate(sizeForm);
            if (!CollectionUtils.isEmpty(resultValidate)) {
                ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, resultValidate);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(sizeService.save(sizeForm)));
                return ResponseEntity.ok(response);
            }
    }
}
