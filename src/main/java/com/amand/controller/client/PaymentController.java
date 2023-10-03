package com.amand.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {

    @GetMapping("giao-dich-thanh-cong")
    public ModelAndView paymentSuccess() {

        return null;
    }

    @GetMapping("giao-dich-that-bai")
    public ModelAndView paymentFailed() {

        return null;
    }
}
