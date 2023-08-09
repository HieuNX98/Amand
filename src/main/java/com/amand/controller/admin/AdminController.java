package com.amand.controller.admin;

import com.amand.Utils.SecurityUtils;
import com.amand.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/home")
    public ModelAndView home(HttpSession session){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        String fullName = null;
        String usernName = null;
        if (SecurityUtils.getPrincipal().getFullName() != null || SecurityUtils.getPrincipal().getUsername() != null){
            fullName = SecurityUtils.getPrincipal().getFullName();
            usernName = SecurityUtils.getPrincipal().getUsername();
        }
        mav.addObject("userName", usernName);
        mav.addObject("fullName", fullName);
        return mav;
    }

    @GetMapping("/danh-sach-san-pham")
    public ModelAndView listProduct(){
        ModelAndView mav = new ModelAndView("admin/views/ListProduct");
        return mav;
    }

    @GetMapping("/chinh-sua-thong-tin-san-pham")
    public ModelAndView editProduct() {
        ModelAndView mav = new ModelAndView("admin/views/EditProduct");
        return mav;
    }

    @GetMapping("/them-san-pham")
    public ModelAndView createProduct() {
         ModelAndView mav = new ModelAndView("/admin/views/CreateProduct");
        return mav;
    }

    @GetMapping("/them-the-loai")
    public ModelAndView createCategory() {
        ModelAndView mav = new ModelAndView("admin/views/CreateCategory");
        return mav;
    }

    @GetMapping("/danh-sach-the-loai-san-pham")
    public ModelAndView listCategory(){
        ModelAndView mav = new ModelAndView("admin/views/ListCategories");
        return mav;
    }

    @GetMapping("/chinh-sua-the-loai-san-pham")
    public ModelAndView editCategory() {
        ModelAndView mav = new ModelAndView("admin/views/EditCategory");
        return mav;
    }

    @GetMapping("/them-mau-sac")
    public ModelAndView createColor() {
        ModelAndView mav = new ModelAndView("admin/views/CreateColor");
        return mav;
    }

    @GetMapping("/them-kich-thuoc")
    public ModelAndView createSize() {
        ModelAndView mav = new ModelAndView("admin/views/CreateSize");
        return mav;
    }

    @GetMapping("/them-anh")
    public ModelAndView createImage() {
        ModelAndView mav = new ModelAndView("admin/views/CreateImage");
        return mav;
    }

    @GetMapping("/chinh-sua-anh-san-pham")
    public ModelAndView editImage() {
        ModelAndView mav = new ModelAndView("admin/views/EditImage");
        return mav;
    }

    @GetMapping("/danh-sach-anh-san-pham")
    public ModelAndView listImage() {
        ModelAndView mav = new ModelAndView("admin/views/ListImage");
        return mav;
    }

    @GetMapping("/them-mua")
    public ModelAndView createSeason() {
        ModelAndView mav = new ModelAndView("admin/views/CreateSeason");
        return mav;
    }

    @GetMapping("/tao-don-hang")
    public ModelAndView createOder() {
        ModelAndView mav = new ModelAndView("admin/views/CreateOder");
        return mav;
    }

    @GetMapping("/chinh-sua-don-hang")
    public ModelAndView editOder() {
        ModelAndView mav = new ModelAndView("admin/views/EditOder");
        return mav;
    }

    @GetMapping("/danh-sach-don-hang")
    public ModelAndView listOders() {
        ModelAndView mav = new ModelAndView("admin/views/ListOders");
        return mav;
    }

    @GetMapping("/tao-tai-khoan-admin")
    public ModelAndView createAdminAccount(){
        ModelAndView mav = new ModelAndView("admin/views/CreateAdminAccount");
        mav.addObject("roles", roleService.findAll());
        return mav;
    }

    @GetMapping("/danh-sach-tai-khoan-admin")
    public ModelAndView listAdminAccount() {
        ModelAndView mav = new ModelAndView("admin/views/ListAdminAccount");
        return mav;
    }

}
