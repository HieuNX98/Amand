package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.ColorDto;
import com.amand.form.ColorForm;
import com.amand.service.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class ColorController {

    @Autowired
    private IColorService colorService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/them-mau-sac")
    public ModelAndView createColor() {
        ModelAndView mav = new ModelAndView("admin/views/CreateColor");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/danh-sach-mau-san-pham")
    public ModelAndView listColor(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "limit", defaultValue = "3") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListColor");
        Pageable pageable = PageRequest.of(page -1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<ColorDto> colorDtos = colorService.findAllByStatus(pageable, SystemConstant.ACTIVE_STATUS);
        mav.addObject("colorDtos", colorDtos);
        controllerUtils.setPageAndLimit(mav, page, limit);
        int totalItem = colorService.getTotalItem(SystemConstant.ACTIVE_STATUS);
        mav.addObject("totalPage", (int) Math.ceil((double) totalItem / limit));
        controllerUtils.setModelAndView(mav);

        return mav;
    }

    @GetMapping("/chinh-sua-mau-san-pham")
    public ModelAndView editColor(@RequestParam(value = "id") Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/views/EditColor");
        ColorDto colorDto = colorService.findOneById(id);
        if (colorDto == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError","Màu sản phẩm không tồn tại vui lòng chọn" +
                    "màu khác");
            return mav;
        }
        controllerUtils.setModelAndView(mav);
        mav.addObject("colorDto", colorDto);
        return mav;
    }

    @PostMapping("/chinh-sua-mau-san-pham")
    public ModelAndView editColor(@ModelAttribute ColorForm colorForm, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("admin/views/EditColor");
        Map<String, String> resultValidate = colorService.validate(colorForm, false);
        if (CollectionUtils.isEmpty(resultValidate)) {
            colorService.save(colorForm);
            redirectAttributes.addFlashAttribute("messageSuccess", "Cập nhật thành công");
            mav.setViewName("redirect:/admin/chi-tiet-mau-san-pham?id=" + colorForm.getId());
        } else {
            mav.addObject("messageError", resultValidate);
            mav.addObject("colorDto", colorForm);
            controllerUtils.setModelAndView(mav);
        }
        return mav;
    }

    @GetMapping("/chi-tiet-mau-san-pham")
    public ModelAndView detailColor(@RequestParam(value = "id") Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/views/DetailColor");
        ColorDto colorDto = colorService.findOneById(id);
        if (colorDto == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError", "Màu sản phẩm không tồn tại");
            return mav;
        }
        controllerUtils.setModelAndView(mav);
        mav.addObject("colorDto", colorDto);
        return mav;
    }

    @GetMapping("/danh-sach-mau-bi-an")
    public ModelAndView listHideColor(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "limit", defaultValue = "3") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListHideColor");
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<ColorDto> colorDtos = colorService.findAllByStatus(pageable, SystemConstant.INACTIVE_STATUS);
        mav.addObject("colorDtos", colorDtos);
        controllerUtils.setPageAndLimit(mav, page, limit);
        mav.addObject("totalPages", (int) Math.ceil((double) colorService.getTotalItem(SystemConstant.INACTIVE_STATUS) / limit));
        controllerUtils.setModelAndView(mav);
        return mav;
    }
}
