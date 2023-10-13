package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.SecurityUtils;
import com.amand.Utils.YearUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.CategoryDto;
import com.amand.dto.ProductDto;
import com.amand.service.ICategoryService;
import com.amand.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ControllerUtils controllerUtils;


    @GetMapping("/trang-chu")
    public ModelAndView home(Model model) {
        ModelAndView mav = new ModelAndView("client/views/home");
        List<CategoryDto> categoryDtos = categoryService.findTop3ByStatusAndLimit(SystemConstant.ACTIVE_STATUS, 3);
        if (categoryDtos.size() >= 1) {
            mav.addObject("categoryDto1", categoryDtos.get(0));
        }
        if (categoryDtos.size() >= 2) {
            mav.addObject("categoryDto2", categoryDtos.get(1));
        }
        if (categoryDtos.size() >= 3) {
            mav.addObject("categoryDto3", categoryDtos.get(2));
        }

        ProductDto productDto = productService.outstandingProduct(SystemConstant.ACTIVE_STATUS, 1);
        mav.addObject("productDto", productDto);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;


    }

}
