package com.amand.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ProductInformationController {

    @GetMapping("/thong-tin-san-pham")
    public ModelAndView information() {
       return new ModelAndView("client/views/information");
    }
}
