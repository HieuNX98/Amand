package com.amand.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        return mav;
    }

    @GetMapping("/product-management")
    public  ModelAndView productManagement(){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        return mav;
    }
}
