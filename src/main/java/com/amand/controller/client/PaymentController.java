package com.amand.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    @GetMapping("/giao-dich-thanh-cong")
    public ModelAndView paymentSuccess(Model model, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/client/views/PaymentSuccess");
        if (model == null || model.asMap().isEmpty()) {
            redirectAttributes.addFlashAttribute("messageError", "Thông tin thanh toán không tồn tại");
            mav.setViewName("redirect:/500");
            return mav;
        }
        mav.addObject("vnp_TxnRef", model.getAttribute("vnp_TxnRef"));
        mav.addObject("vnp_Amount", model.getAttribute("vnp_Amount"));
        mav.addObject("vnp_CurrCode", model.getAttribute("vnp_CurrCode"));
        mav.addObject("vnp_BankCode", model.getAttribute("vnp_BankCode"));
        mav.addObject("vnp_OrderInfo", model.getAttribute("vnp_OrderInfo"));
        mav.addObject("Time", model.getAttribute("Time"));
        return mav;
    }

    @GetMapping("/giao-dich-that-bai")
    public ModelAndView paymentFailed() {

        return null;
    }
}
