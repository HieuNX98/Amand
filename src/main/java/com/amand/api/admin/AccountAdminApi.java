package com.amand.api.admin;

import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.form.UserForm;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class AccountAdminApi {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody UserForm userForm) {
        Map<String, String> validateResult = userService.validate(userForm, true);
        if (!CollectionUtils.isEmpty(validateResult)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateResult);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(userService.save(userForm)));
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/edit-account")
    public ResponseEntity<?> updateAccount(@RequestBody UserForm userForm) {
        Map<String, String> validateResult = userService.validateUpdateAccount(userForm);
        if (!CollectionUtils.isEmpty(validateResult)) {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, validateResult);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(userService.save(userForm)));
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/hide-account")
    public ResponseEntity<?> hideAccount(@RequestBody List<Integer> ids) {
        userService.updateStatus(ids, SystemConstant.INACTIVE_STATUS);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/restore-account")
    public ResponseEntity<?> restoreAccount(@RequestBody List<Integer> ids) {
        userService.updateStatus(ids, SystemConstant.ACTIVE_STATUS);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody List<Integer> ids) {
        userService.deleteUser(ids);
        return ResponseEntity.ok().build();
    }


}
