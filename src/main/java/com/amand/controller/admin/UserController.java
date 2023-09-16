package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.RoleDto;
import com.amand.dto.UserDto;
import com.amand.form.UserForm;
import com.amand.service.IRoleService;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/quan-tri/tao-tai-khoan-admin")
    public ModelAndView createAdminAccount() {
        ModelAndView mav = new ModelAndView("admin/views/CreateAdminAccount");
        mav.addObject("roles", roleService.findAll());
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/quan-tri/danh-sach-tai-khoan-admin")
    public ModelAndView listAdminAccount(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "5") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListAdminAccount");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<UserDto> userDtos = userService.findAllByRoleCodeAndStatus(List.of(SystemConstant.ROLE_CODE_IMPLOY,
                        SystemConstant.ROLE_CODE_ADMIN), pageable, SystemConstant.ACTIVE_STATUS);
        int totalItem = userService.getTotalItem(SystemConstant.ACTIVE_STATUS, SystemConstant.ROLE_CODE_IMPLOY);
        mav.addObject("totalPage", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("userDtos", userDtos);
        mav.addObject("page", page);
        mav.addObject("limit", limit);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/quan-tri/chinh-sua-tai-khoan-admin")
    public ModelAndView editAdminAccount(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView("admin/views/EditAdminAccount");
        UserDto userDto = userService.findOneById(id);
        List<String> roleCodes = roleService.getRoleCodesByUserDto(userDto);
        mav.addObject("roleUsers", roleCodes);
        mav.addObject("user", userDto);
        List<RoleDto> roles = roleService.findAll();
        mav.addObject("roles", roles);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/quan-tri/danh-sach-tai-khoan-bi-an")
    public ModelAndView listHideAccount(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "limit", defaultValue = "3") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListHideAdminAccount");
        mav.addObject("page", page);
        mav.addObject("limit", limit);
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<UserDto> userDtos = userService.findAllByRoleCodeAndStatus(List.of(SystemConstant.ROLE_CODE_IMPLOY,
                SystemConstant.ROLE_CODE_ADMIN), pageable, SystemConstant.INACTIVE_STATUS);
        mav.addObject("userDtos", userDtos);
        int totalItem = userService.getTotalItem(SystemConstant.INACTIVE_STATUS, SystemConstant.ROLE_CODE_IMPLOY);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        controllerUtils.setModelAndView(mav);
        return mav;

    }

    @GetMapping("/thong-tin-tai-khoan")
    public ModelAndView accountInformation() {
        ModelAndView mav = new ModelAndView("admin/views/AccountInformation");
        String userName = SecurityUtils.getPrincipal().getUsername();
        UserDto userDto = userService.findOneByUserName(userName);
        mav.addObject("userDto", userDto);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

 }
