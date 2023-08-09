package com.amand.api;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.dto.UserDto;
import com.amand.service.IUserService;
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
public class AdminApi {

    @Autowired
    private IUserService userService;

    @PostMapping("/admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserDto userDto) {
        Map<String, String> validateResult = userService.validate(userDto, true);
        if (!CollectionUtils.isEmpty(validateResult)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateResult);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(userService.save(userDto)));
            return ResponseEntity.ok(response);
        }
    }
}
