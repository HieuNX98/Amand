package com.amand.controller.admin;

import com.amand.Utils.ControllerUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.SizeDto;
import com.amand.form.HideForm;
import com.amand.form.SizeForm;
import com.amand.service.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping("/them-kich-thuoc")
    public ModelAndView createSize() {
        ModelAndView mav = new ModelAndView("admin/views/CreateSize");
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @GetMapping("/danh-sach-kich-thuoc")
    public ModelAndView listSize(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam (value = "limit", defaultValue = "3") Integer limit,
                                 Model model) {
        ModelAndView mav = new ModelAndView("admin/views/ListSize");
        controllerUtils.setPageAndLimit(mav, page, limit);
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<SizeDto> sizeDtos = sizeService.findAll(pageable, SystemConstant.ACTIVE_STATUS);
        mav.addObject("sizeDtos", sizeDtos);
        int totalItem = sizeService.getTotalItem(SystemConstant.ACTIVE_STATUS);
        mav.addObject("totalPages",(int) Math.ceil((double) totalItem / limit));
        mav.addObject("messageError", model.getAttribute("messageError"));
        mav.addObject("messageSuccess", model.getAttribute("messageSuccess"));
        controllerUtils.setModelAndView(mav);
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
        controllerUtils.setModelAndView(mav);
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
            controllerUtils.setModelAndView(mav);
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
        controllerUtils.setModelAndView(mav);
        return mav;
    }

    @PostMapping("/an-kich-thuoc")
    public ModelAndView hideSize(@ModelAttribute HideForm form, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
            sizeService.updateStatus(form.getIds(), SystemConstant.INACTIVE_STATUS);
            redirectAttributes.addFlashAttribute("messageSuccess", "Cập nhật thành công");
            mav.setViewName("redirect:/admin/danh-sach-kich-thuoc-bi-an");
        return mav;
    }

    @GetMapping("/danh-sach-kich-thuoc-bi-an")
        public ModelAndView listHideSize(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "limit", defaultValue = "3") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListHideSize");
        controllerUtils.setPageAndLimit(mav, page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "modifiedDate"));
        List<SizeDto> sizeDtos = sizeService.findAll(pageable, SystemConstant.INACTIVE_STATUS);
        mav.addObject("sizeDtos", sizeDtos);
        mav.addObject("totalPages", (int) Math.ceil((double) sizeService.getTotalItem(SystemConstant.INACTIVE_STATUS) / limit));
        controllerUtils.setModelAndView(mav);
        return  mav;
    }



}
