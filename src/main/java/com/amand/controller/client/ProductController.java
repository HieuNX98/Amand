package com.amand.controller.client;

import com.amand.Utils.ControllerUtils;
import com.amand.Utils.YearUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.ProductDto;
import com.amand.service.IProductService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/danh-sach-san-pham")
    public ModelAndView listProduct(@RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "season", required = false) String season,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit) {
        ModelAndView mav = new ModelAndView("/client/views/ListProduct");
        mav.addObject("years", yearUtils.getYears());
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<ProductDto> productDtos = productService.search(pageable, productName, season, categoryId, false);
        int totalItem = productService.getTotalItemBySearch(productName, season, categoryId, false);
        mav.addObject("season", season);
        mav.addObject("productName", productName);
        mav.addObject("productDtos", productDtos);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("categoryId", categoryId);
        controllerUtils.setPageAndLimit(mav, page, limit);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }

    @GetMapping("/thong-tin-san-pham")
    public ModelAndView information(@RequestParam(value = "productId") Integer productId,
                                    RedirectAttributes redirectAttributes) {
       ModelAndView mav = new ModelAndView("client/views/information");
       ProductDto productDto = productService.findOneById(productId);
       if (productDto == null) {
           mav.setViewName("redirect:/404");
           redirectAttributes.addFlashAttribute("messageError", "Sản phẩm không tồn tại");
           return mav;
       }
       mav.addObject("productDto", productDto);
       controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }

    @GetMapping("/danh-sach-san-pham-moi")
    public ModelAndView newProductList(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                       @RequestParam(value = "productName", required = false) String productName,
                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", defaultValue = "3") Integer limit) {
        ModelAndView mav = new ModelAndView("/client/views/NewListProduct");
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        controllerUtils.setPageAndLimit(mav, page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);
        String year = yearUtils.getCurrentYear();
        List<ProductDto> productDtos = productService.search(pageable, productName, year, categoryId, false);
        mav.addObject("productDtos", productDtos);
        int totalItem = productService.getTotalItemBySearch(productName, year, categoryId, false);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("productName", productName);
        mav.addObject("categoryId", categoryId);
        return mav;
    }

    @GetMapping("/danh-sach-san-pham-sale")
    public ModelAndView listProductSale(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                        @RequestParam(value = "productName", required = false) String productName,
                                        @RequestParam(value = "season", required = false) String season,
                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "limit", defaultValue = "3") Integer limit){
        ModelAndView mav = new ModelAndView("/client/views/SaleListProduct");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<ProductDto> productDtos = productService.search(pageable, productName, season, categoryId, true);
        int totalItem = productService.getTotalItemBySearch(productName, season, categoryId, true);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("productDtos", productDtos);
        controllerUtils.setPageAndLimit(mav, page, limit);
        mav.addObject("season", season);
        mav.addObject("productName", productName);
        mav.addObject("categoryId", categoryId);
        mav.addObject("years", yearUtils.getYears());
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        return mav;
    }

    @GetMapping("/tim-kiem-san-pham")
    public ModelAndView searchProduct(@RequestParam(value = "productName", required = false) String productName,
                                      @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "3") Integer limit,
                                      @RequestParam(value = "season", required = false) String season) {
        ModelAndView mav = new ModelAndView("/client/views/SearchProduct");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<ProductDto> productDtos = productService.search(pageable, productName, season, categoryId, false);
        int totalItem = productService.getTotalItemBySearch(productName, season, categoryId, false);
        mav.addObject("totalPages", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("productDtos", productDtos);
        mav.addObject("season", season);
        mav.addObject("categoryId", categoryId);
        mav.addObject("productName", productName);
        controllerUtils.setPageAndLimit(mav, page, limit);
        controllerUtils.setModelAndViewClient(mav, SystemConstant.ACTIVE_STATUS);
        mav.addObject("years", yearUtils.getYears());
        return mav;
    }
}
