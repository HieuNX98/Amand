package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.dto.UserDto;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminHomeController {
    
    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

}
