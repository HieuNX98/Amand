package com.amand.Utils;

import com.amand.dto.CategoryDto;
import com.amand.dto.ProductDto;
import com.amand.dto.UserDto;
import com.amand.service.ICategoryService;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.List;

@Component
public class ControllerUtils {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    public void setModelAndView(ModelAndView mav) {
        UserDto userDto = new UserDto();
        if (SecurityUtils.getPrincipal() != null) {
            String userName = SecurityUtils.getPrincipal().getUsername();
            userDto = userService.findOneByUserName(userName);
        }
        mav.addObject("userDto", userDto);
    }

    public void setModelAndViewClient(ModelAndView mav, Integer status) {
        List<CategoryDto> categoryDtos = categoryService.findAllByStatus(status);
        String fullName = null;
        if (SecurityUtils.getPrincipal() != null) {
            fullName = SecurityUtils.getPrincipal().getFullName();
        }
        mav.addObject("fullName", fullName);
        mav.addObject("categoryDtos", categoryDtos);
    }

    public void setPageAndLimit(ModelAndView mav, Integer page, Integer limit) {
        mav.addObject("page", page);
        mav.addObject("limit", limit);
    }

    public void searchRoles(ModelAndView mav) {
        List<String> roles = SecurityUtils.getAuthorities();
        boolean isAdmin = false;
        for (String role : roles) {
            if ("ROLE_ADMIN".equals(role)) {
                isAdmin = true;
                break;
            }
        }
        mav.addObject("isAdmin", isAdmin);
    }
}
