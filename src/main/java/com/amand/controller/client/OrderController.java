package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.BagDto;
import com.amand.dto.ProductBagDto;
import com.amand.dto.UserDto;
import com.amand.service.IBagService;
import com.amand.service.IProductBagService;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBagService bagService;

    @Autowired
    private IProductBagService productBagService;


    @GetMapping("/thanh-toan")
    public ModelAndView payment() {
        ModelAndView mav = new ModelAndView("/client/views/Payment");
        int userId = SecurityUtils.getPrincipal().getUserId();
        UserDto userDto = userService.findOneById(userId);
        mav.addObject("userDto", userDto);
        BagDto bagDto = bagService.findByUserId(userId);
        mav.addObject("bagDto", bagDto);
        List<ProductBagDto> productBagDtos = productBagService.findAllByBagId(bagDto.getId());
        mav.addObject("productBagDtos", productBagDtos);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }
}
