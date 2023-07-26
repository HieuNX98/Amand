package com.amand.controller.admin;

import com.amand.Utils.SecurityUtils;
import com.amand.dto.MyUser;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("/home")
    public ModelAndView home(HttpSession session){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        return mav;
    }

    @GetMapping("/product-management")
    public  ModelAndView productManagement(){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        return mav;
    }
}
