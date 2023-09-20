package com.amand.controller.client;

import com.amand.Utils.YearUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.ProductDto;
import com.amand.service.ICategoryService;
import com.amand.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class ProductController {

    @Autowired
    private YearUtils yearUtils;

    @Autowired
    private IProductService productService;

    @GetMapping("/danh-sach-san-pham")
    public ModelAndView listProduct(@RequestParam(value = "categoryId") Integer categoryId,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "season", required = false) String season,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit,
            RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/client/views/ListProduct");
        if (categoryId == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError", "Danh sách sản phẩm không tồn tại");
            return mav;
        }
        mav.addObject("years", yearUtils.getYears());
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<ProductDto> productDtos = productService.search(pageable, productName, season, categoryId);
        int totalItem = productService.getTotalItemBySearch(productName, season, categoryId);
        mav.addObject("season", season);
        mav.addObject("productName", productName);
        mav.addObject("productDtos", productDtos);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("limit", limit);
        mav.addObject("page", page);
        mav.addObject("categoryId", categoryId);
        return mav;
    }

    @GetMapping("/thong-tin-san-pham")
    public ModelAndView information() {
       return new ModelAndView("client/views/information");
    }
}
