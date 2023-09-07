package com.amand.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class RegisterController {

    @GetMapping("/dang-ky")
    public ModelAndView register() {
        return new ModelAndView("register");
    }
}
