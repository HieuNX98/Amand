package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class OrderController {

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/tao-don-hang")
    public ModelAndView createOder() {
        ModelAndView mav = new ModelAndView("admin/views/CreateOder");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/chinh-sua-don-hang")
    public ModelAndView editOder() {
        ModelAndView mav = new ModelAndView("admin/views/EditOder");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/danh-sach-don-hang")
    public ModelAndView listOders() {
        ModelAndView mav = new ModelAndView("admin/views/ListOders");
        controllerUtils.setModelAndView(mav);
        return mav;
    }
}
