package com.amand.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class BagController {

    @GetMapping("/gio-hang")
    public ModelAndView bag() {
        return new ModelAndView("client/views/bag");
    }
}
