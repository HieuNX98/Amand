package com.amand.controller.admin;

import com.amand.Utils.SecurityUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.CategoryDto;
import com.amand.dto.ProductDto;
import com.amand.dto.UserDto;
import com.amand.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IColorService colorService;

    @Autowired
    private ISizeService sizeService;

    @Autowired
    private  IProductService productService;

    @GetMapping("/home")
    public ModelAndView home(HttpSession session){
        ModelAndView mav = new ModelAndView("/admin/views/home");
        String fullName = "";
        String userName = "";
        if (SecurityUtils.getPrincipal() != null){
            fullName = SecurityUtils.getPrincipal().getFullName();
            userName = SecurityUtils.getPrincipal().getUsername();
        }
        mav.addObject("userName", userName);
        mav.addObject("fullName", fullName);
        return mav;
    }

    @GetMapping("/danh-sach-san-pham")
    public ModelAndView listProduct(@RequestParam (value = "page", defaultValue = "1") int page,
                                    @RequestParam (value = "limit", defaultValue = "5") int limit){
        ModelAndView mav = new ModelAndView("admin/views/ListProduct");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<ProductDto> productDtos = productService.findAll(pageable);
        mav.addObject("productDtos", productDtos);
        int totalPages = (int) Math.ceil((double) productService.getTotalItem() / limit);
        mav.addObject("totalPages", totalPages);
        mav.addObject("limit", limit);
        mav.addObject("page", page);
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
         mav.addObject("categories", categoryService.findAll());
         mav.addObject("colors", colorService.findAll());
         mav.addObject("sizes", sizeService.findAll());
        return mav;
    }

    @GetMapping("/them-the-loai")
    public ModelAndView createCategory() {
        ModelAndView mav = new ModelAndView("admin/views/CreateCategory");
        return mav;
    }

    @GetMapping("/danh-sach-the-loai-san-pham")
    public ModelAndView listCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "5")int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListCategories");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<CategoryDto> categoryDtos = categoryService.findAll(pageable);
        mav.addObject("categoryDtos", categoryDtos);
        int totalItem = categoryService.getTotalItem();
        mav.addObject("totalPage", (int) Math.ceil( (double) totalItem / limit));
        mav.addObject("limit", limit);
        mav.addObject("page", page);
        return mav;
    }

    @GetMapping("/chinh-sua-the-loai-san-pham")
    public ModelAndView editCategory(@RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView("admin/views/EditCategory");
        CategoryDto categoryDto = new CategoryDto();
        if (id != null) {
            categoryDto = categoryService.findOneById(id);
        }
        mav.addObject("categoryDto", categoryDto);
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
    public ModelAndView createAdminAccount() {
        ModelAndView mav = new ModelAndView("admin/views/CreateAdminAccount");
        mav.addObject("roles", roleService.findAll());
        return mav;
    }

    @GetMapping("/danh-sach-tai-khoan-admin")
    public ModelAndView listAdminAccount(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "limit", defaultValue = "5") int limit) {
        ModelAndView mav = new ModelAndView("admin/views/ListAdminAccount");
        Pageable pageable = PageRequest.of(page - 1, limit);
        List<UserDto> userDtos = userService.findAllByRoleCode(SystemConstant.ROLE_CODE, pageable);
        int totalItem = userService.getTotalItem();
        mav.addObject("totalPage", (int) Math.ceil((double) totalItem / limit));
        mav.addObject("userDtos", userDtos);
        mav.addObject("page", page);
        mav.addObject("limit", limit);
        return mav;
    }

}
