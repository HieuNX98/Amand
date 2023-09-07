package com.amand.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class OrderController {

    @GetMapping("/tao-don-hang")
    public ModelAndView createOder() {
        return new ModelAndView("admin/views/CreateOder");
    }

    @GetMapping("/chinh-sua-don-hang")
    public ModelAndView editOder() {
        return new ModelAndView("admin/views/EditOder");
    }

    @GetMapping("/danh-sach-don-hang")
    public ModelAndView listOders() {
        return new ModelAndView("admin/views/ListOders");
    }
}
