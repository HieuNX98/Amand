package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.GetUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.PayDto;
import com.amand.form.PayForm;
import com.amand.service.IOrderService;
import com.amand.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("admin")
public class RevenueController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private IReportService reportService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/quan-tri/doanh-thu")
    public ModelAndView revenue() {
        ModelAndView mav = new ModelAndView("admin/views/Revenue");
        List<PayDto> payDtos = reportService.getRevenueByStringTime(GetUtils.getCurrentTime());
        double totalMoney = reportService.countTotalMoney(payDtos);
        mav.addObject("payDtos", payDtos);
        mav.addObject("totalMoney", totalMoney);
        int orderHasBeenCompleted = orderService.getTotalItem(GetUtils.getCurrentTime(), SystemConstant.ACTIVE_STATUS);
        int orderWasCancelled = orderService.getTotalItem(GetUtils.getCurrentTime(), SystemConstant.CANCEL_STATUS);
        int totalOrder = orderService.getTotalItem(GetUtils.getCurrentTime());
        mav.addObject("orderHasBeenCompleted", orderHasBeenCompleted);
        mav.addObject("orderWasCancelled", orderWasCancelled);
        mav.addObject("totalOrder", totalOrder);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @PostMapping("/quan-tri/doanh-thu")
    public ModelAndView revenue(@ModelAttribute PayForm payForm) {
        ModelAndView mav = new ModelAndView("admin/views/Revenue");
        List<PayDto> payDtos = reportService.getRevenueByForm(payForm);
        double totalMoney = reportService.countTotalMoney(payDtos);
        mav.addObject("payDtos", payDtos);
        mav.addObject("totalMoney", totalMoney);
        mav.addObject("select", payForm.getSelect());
        mav.addObject("sinceDate", payForm.getSinceDate());
        mav.addObject("toDate", payForm.getToDate());
        controllerUtils.setModelAndView(mav);
        return mav;
    }

}
