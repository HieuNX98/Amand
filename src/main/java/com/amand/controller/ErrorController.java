package com.amand.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ErrorController {

    @GetMapping("/403")
    public ModelAndView accessDenied() {
        return new ModelAndView("403");
    }

    @GetMapping("/404")
    public ModelAndView notFound(Model model) {
        ModelAndView mav = new ModelAndView("404");
        mav.addObject("messageError", model.getAttribute("messageError"));
        return mav;
    }

    @GetMapping("/500")
    public ModelAndView statusError(Model model) {
        ModelAndView mav = new ModelAndView("500");
        mav.addObject("messageError", model.getAttribute("messageError"));
        return mav;
    }
}
