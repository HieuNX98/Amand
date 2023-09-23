package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.CategoryDto;
import com.amand.service.ICategoryService;
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
@RequestMapping("admin")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/them-the-loai")
    public ModelAndView createCategory() {
        ModelAndView mav = new ModelAndView("admin/views/CreateCategory");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/danh-sach-the-loai-san-pham")
    public ModelAndView listCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "5") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListCategories");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<CategoryDto> categoryDtos = categoryService.findAllByStatus(pageable, SystemConstant.ACTIVE_STATUS);
        mav.addObject("categoryDtos", categoryDtos);
        int totalItem = categoryService.getTotalItem(SystemConstant.ACTIVE_STATUS);
        mav.addObject("totalPage", (int) Math.ceil( (double) totalItem / limit));
        controllerUtils.setPageAndLimit(mav, page, limit);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/chinh-sua-the-loai-san-pham")
    public ModelAndView editCategory(@RequestParam(value = "id") Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/views/EditCategory");
        CategoryDto categoryDto = categoryService.findOneById(id);
        if (categoryDto == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError", "Thể loại sản phẩm không tồn tại, vui lòng " +
                    "chọn thể loại sản phẩm khác");
            return mav;
        }
        mav.addObject("categoryDto", categoryDto);
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/danh-sach-the-loai-bi-an")
    public ModelAndView listHideCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "3") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListHideCategory");
        controllerUtils.setPageAndLimit(mav, page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<CategoryDto> categoryDtos = categoryService.findAllByStatus(pageable, SystemConstant.INACTIVE_STATUS);
        mav.addObject("categoryDtos", categoryDtos);
        mav.addObject("totalPages", (int) Math.ceil( (double) categoryService.getTotalItem(SystemConstant.INACTIVE_STATUS) / limit));
        controllerUtils.setModelAndView(mav);
        return mav;
    }

}
