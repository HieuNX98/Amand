package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.CategoryDto;
import com.amand.dto.ColorDto;
import com.amand.dto.ProductDto;
import com.amand.dto.SizeDto;
import com.amand.service.ICategoryService;
import com.amand.service.IColorService;
import com.amand.service.IProductService;
import com.amand.service.ISizeService;
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
public class AdminProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IColorService colorService;

    @Autowired
    private ISizeService sizeService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/danh-sach-san-pham")
    public ModelAndView listProduct(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam (value = "limit", defaultValue = "5") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListProduct");
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<ProductDto> productDtos = productService.findAll(pageable, SystemConstant.ACTIVE_STATUS);
        mav.addObject("productDtos", productDtos);
        int totalPages = (int) Math.ceil((double) productService.getTotalItem(SystemConstant.ACTIVE_STATUS) / limit);
        mav.addObject("totalPages", totalPages);
        controllerUtils.setPageAndLimit(mav, page, limit);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/chinh-sua-thong-tin-san-pham")
    public ModelAndView editProduct(@RequestParam(value = "id", required = false) Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/views/EditProduct");
        ProductDto productDto = productService.findOneById(id);
        if (productDto == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError", "Sản phẩm không tồn tại, vui lòng chọn sản phẩm khác");
            return mav;
        }
        mav.addObject("productDto", productDto);
        List<Integer> colorIds = productDto.getColorIds();
        mav.addObject("colorIds", colorIds);

        Integer categoryId = productDto.getCategoryId();
        CategoryDto categoryDto = categoryService.findOneById(categoryId);
        mav.addObject("categoryDto", categoryDto);

        List<Integer> sizeIds = productDto.getSizeIds();
        mav.addObject("sizeIds", sizeIds);

        List<CategoryDto> categoryDtos = categoryService.findAllByStatus(SystemConstant.ACTIVE_STATUS);
        mav.addObject("categoryDtos", categoryDtos);

        List<ColorDto> colorDtos = colorService.findAllByStatus(SystemConstant.ACTIVE_STATUS);
        mav.addObject("colorDtos", colorDtos);

        List<SizeDto> sizeDtos = sizeService.findAllByStatus(SystemConstant.ACTIVE_STATUS);
        mav.addObject("sizeDtos", sizeDtos);

        controllerUtils.setModelAndView(mav);

        return mav;
    }

    @GetMapping("/them-san-pham")
    public ModelAndView createProduct() {
        ModelAndView mav = new ModelAndView("admin/views/CreateProduct");
        mav.addObject("categories", categoryService.findAllByStatus(SystemConstant.ACTIVE_STATUS));
        mav.addObject("colors", colorService.findAllByStatus(SystemConstant.ACTIVE_STATUS));
        mav.addObject("sizes", sizeService.findAllByStatus(SystemConstant.ACTIVE_STATUS));
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/them-file-san-pham")
    public ModelAndView createFileProduct() {
        ModelAndView mav = new ModelAndView("admin/views/CreateFileProduct");
        controllerUtils.setModelAndView(mav);
        return mav;
    }


    @GetMapping("/danh-sach-san-pham-bi-an")
    public ModelAndView listHideProduct(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "limit", defaultValue = "3") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListHideProduct");
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<ProductDto> productDtos = productService.findAll(pageable, SystemConstant.INACTIVE_STATUS);
        mav.addObject("productDtos", productDtos);
        controllerUtils.setPageAndLimit(mav, page, limit);
        mav.addObject("totalPages", (int) Math.ceil((double) productService.getTotalItem(SystemConstant.INACTIVE_STATUS) / limit));
        controllerUtils.setModelAndView(mav);
        return mav;
    }
}
