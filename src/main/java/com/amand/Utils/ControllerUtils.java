package com.amand.Utils;

import com.amand.dto.UserDto;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ControllerUtils {

    @Autowired
    private IUserService userService;

    public void setModelAndView(ModelAndView mav) {
        UserDto userDto = new UserDto();
        if (SecurityUtils.getPrincipal() != null) {
            String userName = SecurityUtils.getPrincipal().getUsername();
            userDto = userService.findOneByUserName(userName);
        }
        mav.addObject("userDto", userDto);
    }
}
