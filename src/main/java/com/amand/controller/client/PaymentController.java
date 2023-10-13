package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.BagDto;
import com.amand.dto.ProductBagDto;
import com.amand.dto.ProductDto;
import com.amand.dto.UserDto;
import com.amand.form.ProductForm;
import com.amand.service.IBagService;
import com.amand.service.IProductBagService;
import com.amand.service.IProductService;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class PaymentController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBagService bagService;

    @Autowired
    private IProductBagService productBagService;

    @Autowired
    private IProductService productService;


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
        ModelAndView mav = new ModelAndView("/client/views/PaymentFailed");
        return mav;
    }


    @GetMapping("/thanh-toan")
    public ModelAndView paymentBag() {
        ModelAndView mav = new ModelAndView("/client/views/PaymentBag");
        int userId = Objects.requireNonNull(SecurityUtils.getPrincipal()).getUserId();
        UserDto userDto = userService.findOneById(userId);
        mav.addObject("userDto", userDto);
        BagDto bagDto = bagService.findByUserId(userId);
        mav.addObject("bagDto", bagDto);
        List<ProductBagDto> productBagDtos = bagDto != null ? productBagService.findAllByBagId(bagDto.getId()) : Collections.emptyList();
        mav.addObject("productBagDtos", productBagDtos);
        mav.addObject("transportFee", SystemConstant.TRANSPORT_FEE);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }

    @PostMapping("/thanh-toan")
    public ModelAndView payment(@ModelAttribute ProductForm productForm,
                                RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/client/views/Payment");
        ProductDto productDto = productService.findOneById(productForm.getId());
        mav.addObject("productForm", productForm);
        double subtotal;
        if (productDto.getSalePrice() == null) {
            subtotal = productDto.getOldPrice() * productForm.getAmount();
        } else {
            subtotal = productDto.getSalePrice() * productForm.getAmount();
        }
        mav.addObject("subtotal", subtotal);
        double totalPrice = subtotal + SystemConstant.TRANSPORT_FEE;
        mav.addObject("transportFee", SystemConstant.TRANSPORT_FEE);
        mav.addObject("totalPrice", totalPrice);
        mav.addObject("productDto", productDto);
        int userId = Objects.requireNonNull(SecurityUtils.getPrincipal()).getUserId();
        UserDto userDto = userService.findOneById(userId);
        mav.addObject("userDto", userDto);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }

}
