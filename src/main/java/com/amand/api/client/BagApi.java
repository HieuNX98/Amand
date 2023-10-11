package com.amand.api.client;

import com.amand.entity.ProductBagEntity;
import com.amand.entity.ProductEntity;
import com.amand.form.BagForm;
import com.amand.service.IBagService;
import com.amand.service.IProductBagService;
import com.amand.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class BagApi {

    @Autowired
    private IBagService bagService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductBagService productBagService;

    @PostMapping("/add-bag")
    public ResponseEntity<?> addBag(@RequestBody BagForm bagForm) {

        if (bagService.addBag(bagForm)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete-product-in-the-bag")
    public ResponseEntity<?> deleteProductInTheBag(@RequestBody Integer id) {
        ProductBagEntity productBagEntity = productBagService.findById(id);
        ProductEntity productEntity = productService.findById(productBagEntity.getProduct().getId());
        productEntity.setAmount(productEntity.getAmount() + productBagEntity.getAmount());
        productService.save(productEntity);
        bagService.deleteByProductBagId(id);
        return ResponseEntity.ok().build();
    }
}
