package com.amand.controller;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class ErrorController {

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/403")
    public ModelAndView accessDenied() {
        ModelAndView mav = new ModelAndView("403");
        List<String> roles = SecurityUtils.getAuthorities();
        boolean isImploy = false;
        for (String role : roles) {
            if("ROLE_IMPLOY".equals(role)) {
                isImploy = true;
                break;
            }
        }
        mav.addObject("isImploy", isImploy);
        return mav ;
    }

    @GetMapping("/404")
    public ModelAndView notFound(Model model) {
        ModelAndView mav = new ModelAndView("404");
        mav.addObject("messageError", model.getAttribute("messageError"));
        controllerUtils.searchRoles(mav);
        return mav;
    }

    @GetMapping("/500")
    public ModelAndView statusError(Model model) {
        ModelAndView mav = new ModelAndView("500");
        controllerUtils.searchRoles(mav);
        mav.addObject("messageError", model.getAttribute("messageError"));
        return mav;
    }
}
