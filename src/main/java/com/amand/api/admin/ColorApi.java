package com.amand.api.admin;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.form.ColorForm;
import com.amand.service.IColorService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ColorApi {

    @Autowired
    private IColorService colorService;

    @PostMapping("/color")
    public ResponseEntity<?> createColor(@RequestBody ColorForm colorForm) {
        Map<String, String> validateResult = colorService.validate(colorForm, true);
        if (!CollectionUtils.isEmpty(validateResult)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateResult);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(colorService.save(colorForm)));
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/color")
    public ResponseEntity<?> hide(@RequestBody List<Integer> ids) {
        String validateResult = colorService.validateHide(ids);
        if (Strings.isBlank(validateResult)) {
            colorService.hide(ids);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(validateResult);
    }

}
