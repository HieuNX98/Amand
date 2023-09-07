package com.amand.controller.client;

import com.amand.Utils.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/trang-chu")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("client/views/home");
        String fullName = null;
        if (SecurityUtils.getPrincipal() != null) {
            fullName = SecurityUtils.getPrincipal().getFullName();
        }
        mav.addObject("fullName", fullName);
        return mav;
    }

}
