package com.amand.controller.admin;

import com.amand.constant.SystemConstant;
import com.amand.dto.SizeDto;
import com.amand.form.HideForm;
import com.amand.form.SizeForm;
import com.amand.service.ISizeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class SizeController {
    @Autowired
    private ISizeService sizeService;

    @GetMapping("/them-kich-thuoc")
    public ModelAndView createSize() {
        return new ModelAndView("admin/views/CreateSize");
    }

    @GetMapping("/danh-sach-kich-thuoc")
    public ModelAndView listSize(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam (value = "limit", defaultValue = "3") Integer limit,
                                 Model model) {
        ModelAndView mav = new ModelAndView("admin/views/ListSize");
        mav.addObject("page", page);
        mav.addObject("limit", limit);
        Pageable pageable = PageRequest.of(page-1, limit);
        List<SizeDto> sizeDtos = sizeService.findAllByStatus(pageable, SystemConstant.ACTIVE_STATUS);
        mav.addObject("sizeDtos", sizeDtos);
        int totalItem = sizeService.getTotalItem(SystemConstant.ACTIVE_STATUS);
        mav.addObject("totalPages",(int) Math.ceil((double) totalItem / limit));
        mav.addObject("messageError", model.getAttribute("messageError"));
        mav.addObject("messageSuccess", model.getAttribute("messageSuccess"));
        return mav;
    }

    @GetMapping("/chinh-sua-kich-thuoc")
    public ModelAndView editSize(@RequestParam(value = "id") Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("/admin/views/EditSize");
        SizeDto sizeDto = sizeService.findOneById(id);
        if (sizeDto == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError", "Kích thước sản phẩm không tồn tại");
            return mav;
        }
        mav.addObject("sizeDto", sizeDto);
        return mav;
    }

    @PostMapping("/chinh-sua-kich-thuoc")
    public ModelAndView editSize(@ModelAttribute SizeForm sizeForm, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/views/EditSize");
        Map<String, String> validate = sizeService.validate(sizeForm, false);
        if (CollectionUtils.isEmpty(validate)) {
            redirectAttributes.addFlashAttribute("messageSuccess", "Cập nhật thành công");
            sizeService.save(sizeForm);
            mav.setViewName("redirect:/admin/chi-tiet-kich-thuoc-san-pham?id=" + sizeForm.getId());
        } else {
            mav.addObject("messageError", validate);
            mav.addObject("sizeDto", sizeForm);
        }

        return mav;
    }

    @GetMapping("/chi-tiet-kich-thuoc-san-pham")
    public ModelAndView detailSize(@RequestParam(value = "id") Integer id, RedirectAttributes redirectAttributes,
                                   Model model) {
        ModelAndView mav = new ModelAndView("admin/views/DetailSize");
        SizeDto sizeDto = sizeService.findOneById(id);
        if (sizeDto == null) {
            mav.setViewName("redirect:/404");
            redirectAttributes.addFlashAttribute("messageError", "Kích thước sản phẩm không tồn tại");
            return mav;
        }
        mav.addObject("sizeDto", sizeDto);
        mav.addObject("messageSuccess", model.getAttribute("messageSuccess"));
        return mav;
    }

    @PostMapping("/an-kich-thuoc")
    public ModelAndView hideSize(@ModelAttribute HideForm form, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        String resultValidate = sizeService.validateHide(form.getIds());
        if (Strings.isBlank(resultValidate)) {
            sizeService.hide(form.getIds());
            redirectAttributes.addFlashAttribute("messageSuccess", "Cập nhật thành công");
            mav.setViewName("redirect:/admin/danh-sach-kich-thuoc");
        } else {
            redirectAttributes.addFlashAttribute("messageError", resultValidate);
            mav.setViewName("redirect:/admin/danh-sach-kich-thuoc");
        }
        return mav;
    }



}
