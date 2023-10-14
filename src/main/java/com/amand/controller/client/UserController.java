package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.UserDto;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/thong-tin-tai-khoan")
    public ModelAndView accountInformation() {
        ModelAndView mav = new ModelAndView("client/views/AccountInformation");
        int userId = SecurityUtils.getPrincipal().getUserId();
        UserDto userDto = userService.findOneById(userId);
        mav.addObject("userDto", userDto);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }
}
