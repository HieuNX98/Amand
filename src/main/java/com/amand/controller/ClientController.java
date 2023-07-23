package com.amand.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ClientController {

    @GetMapping("/trang-chu")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("client/views/home");
        return mav;
    }

    @GetMapping("/gio-hang")
    public ModelAndView bag() {
        ModelAndView mav = new ModelAndView("client/views/bag");
        return mav;
    }

    @GetMapping("/dang-ky")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register");
        return mav;
    }

    @GetMapping("/dang-nhap")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @GetMapping("/thong-tin-san-pham")
    public ModelAndView information() {
        ModelAndView mav = new ModelAndView("client/views/information");
        return mav;
    }
}
