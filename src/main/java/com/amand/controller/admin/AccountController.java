package com.amand.controller.admin;

import com.amand.constant.SystemConstant;
import com.amand.dto.RoleDto;
import com.amand.dto.UserDto;
import com.amand.service.IRoleService;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AccountController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping("/tao-tai-khoan-admin")
    public ModelAndView createAdminAccount() {
        ModelAndView mav = new ModelAndView("admin/views/CreateAdminAccount");
        mav.addObject("roles", roleService.findAll());
        return mav;
    }

    @GetMapping("/danh-sach-tai-khoan-admin")
    public ModelAndView listAdminAccount(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "5") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListAdminAccount");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<UserDto> userDtos = userService.findAllByRoleCodeAndStatus(SystemConstant.ROLE_CODE_IMPLOY, pageable, SystemConstant.ACTIVE_STATUS);
        int totalItem = userService.getTotalItem(SystemConstant.ACTIVE_STATUS, SystemConstant.ROLE_CODE_IMPLOY);
        mav.addObject("totalPage", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("userDtos", userDtos);
        mav.addObject("page", page);
        mav.addObject("limit", limit);
        return mav;
    }

    @GetMapping("/chinh-sua-tai-khoan-admin")
    public ModelAndView editAdminAccount(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView("/admin/views/EditAdminAccount");
        UserDto userDto = userService.findOneById(id);
        List<String> roleCodes = roleService.getRoleCodesByUserDto(userDto);
        mav.addObject("roleUsers", roleCodes);
        mav.addObject("userDto", userDto);
        List<RoleDto> roles = roleService.findAll();
        mav.addObject("roles", roles);
        return mav;
    }
}
