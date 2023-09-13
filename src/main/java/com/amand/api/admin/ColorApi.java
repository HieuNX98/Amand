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
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/hide-color")
    public ResponseEntity<?> hideColor(@RequestBody List<Integer> ids) {
            colorService.updateStatus(ids, SystemConstant.INACTIVE_STATUS);
            return ResponseEntity.ok().build();
    }

    @PutMapping("/restore-color")
    public ResponseEntity<?> restoreColor(@RequestBody List<Integer> ids) {
        colorService.updateStatus(ids, SystemConstant.ACTIVE_STATUS);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-color")
    public ResponseEntity<?> deleteColor(@RequestBody List<Integer> ids) {
        colorService.deleteColor(ids);
        return ResponseEntity.ok().build();
    }

}
