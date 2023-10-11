package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.OrderDto;
import com.amand.dto.ProductOrderDto;
import com.amand.service.ICategoryService;
import com.amand.service.IOrderService;
import com.amand.service.IProductOrderService;
import com.amand.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminOrderController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductOrderService productOrderService;

    @GetMapping("/chinh-sua-don-hang")
    public ModelAndView editOder() {
        ModelAndView mav = new ModelAndView("admin/views/EditOder");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/danh-sach-don-hang")
    public ModelAndView listOders(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListOders");
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<OrderDto> orderDtos = orderService.findAllByStatus(pageable, SystemConstant.ACTIVE_STATUS);
        int totalItem = orderService.getTotalItem(SystemConstant.ACTIVE_STATUS);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("orderDtos", orderDtos);
        controllerUtils.setPageAndLimit(mav, page, limit);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/chi-tiet-don-hang")
    public ModelAndView detailOrder(@RequestParam(value = "orderId", required = false) Integer orderId,
                                    RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/views/DetailOrder");
        if (orderId == null) {
            redirectAttributes.addFlashAttribute("messageError", "Đơn hàng không tồn tại");
            mav.setViewName("redirect:/500");
            return mav;
        }
            List<ProductOrderDto> productOrderDtos = productOrderService.findAllByOrderId(orderId, SystemConstant.ACTIVE_STATUS);
            mav.addObject("productOrderDtos", productOrderDtos);
            controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/don-hang-moi")
    public ModelAndView newOrder(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        ModelAndView mav= new ModelAndView("admin/views/NewOrders");
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<OrderDto> orderDtos = orderService.findAllByStatus(pageable, SystemConstant.INACTIVE_STATUS);
        mav.addObject("orderDtos", orderDtos);
        mav.addObject("totalPages", (int) Math.ceil((double) orderService.getTotalItem(SystemConstant.INACTIVE_STATUS)
                / limit));
        controllerUtils.setPageAndLimit(mav, page, limit);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/chi-tiet-don-hang-moi")
    public ModelAndView detailNewOrders(@RequestParam(value = "orderId", required = false) Integer orderId,
                                        RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/views/DetailNewOrder");
        if (orderId == null) {
            redirectAttributes.addFlashAttribute("messageError", "Đơn hàng không tồn tại");
            mav.setViewName("redirect:/500");
            return mav;
        }
        List<ProductOrderDto> productOrderDtos = productOrderService.findAllByOrderId(orderId, SystemConstant.INACTIVE_STATUS);
        mav.addObject("productOrderDtos", productOrderDtos);
        mav.addObject("orderId", orderId);
        controllerUtils.setModelAndView(mav);
        return mav;
    }
}
