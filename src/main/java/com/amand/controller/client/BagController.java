package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class BagController {

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/gio-hang")
    public ModelAndView bag() {
        ModelAndView mav = new ModelAndView("client/views/bag");
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }
}
