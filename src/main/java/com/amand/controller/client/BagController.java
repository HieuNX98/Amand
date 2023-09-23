package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.BagDto;
import com.amand.service.IBagService;
import com.amand.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class BagController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private IBagService bagService;

    @GetMapping("/gio-hang")
    public ModelAndView bag() {
        ModelAndView mav = new ModelAndView("client/views/bag");
        int userId = SecurityUtils.getPrincipal().getUserId();
        BagDto bagDto = bagService.findByUserId(userId);
        mav.addObject("bagDto", bagDto);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }
}
