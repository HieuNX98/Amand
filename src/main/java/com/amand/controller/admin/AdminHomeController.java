package com.amand.controller.admin;

import com.amand.Utils.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminHomeController {

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        String fullName = "";
        String userName = "";
        if (SecurityUtils.getPrincipal() != null){
            fullName = SecurityUtils.getPrincipal().getFullName();
            userName = SecurityUtils.getPrincipal().getUsername();
        }
        mav.addObject("userName", userName);
        mav.addObject("fullName", fullName);
        return mav;
    }

}
