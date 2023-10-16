package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.*;
import com.amand.entity.OrderEntity;
import com.amand.entity.ProductBagEntity;
import com.amand.service.IBagService;
import com.amand.service.IOrderService;
import com.amand.service.IProductOrderService;
import com.amand.service.IUserService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private IProductOrderService productOrderService;

    @Autowired
    private IUserService userService;

    @GetMapping("/don-hang-dang-cho")
     public ModelAndView waitForConfirmation() {
         ModelAndView mav = new ModelAndView("client/views/WaitForConfirmation");
         List<OrderDto> orderDtos = orderService.findAllByStatus(SystemConstant.INACTIVE_STATUS);
         controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
         mav.addObject("orderDtos", orderDtos);
         return mav;
     }

     @GetMapping("/chi-tiet-don-hang-dang-cho")
    public ModelAndView detailOrderIsWaiting(@RequestParam(value = "id", required = false) Integer id,
                                             RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("client/views/DetailOrderIsWaiting");
         OrderDto orderDto = orderService.findById(id, SystemConstant.INACTIVE_STATUS);
         if (orderDto == null) {
             mav.setViewName("redirect:/404");
             redirectAttributes.addFlashAttribute("messageError", "Đơn hàng không tồn tại");
             return mav;
         }
         int userId = Objects.requireNonNull(SecurityUtils.getPrincipal()).getUserId();
         UserDto userDto = userService.findOneById(userId);
         mav.addObject("userDto", userDto);
         List<ProductOrderDto> productOrderDtos = productOrderService.findAllByOrderId(id);
        mav.addObject("productOrderDtos", productOrderDtos);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        mav.addObject("orderDto", orderDto);
        return mav;
     }
}
