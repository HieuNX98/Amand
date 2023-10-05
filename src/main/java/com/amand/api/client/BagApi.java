package com.amand.api.client;

import com.amand.form.BagForm;
import com.amand.service.IBagService;
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

    @PostMapping("/add-bag")
    public ResponseEntity<?> addBag(@RequestBody BagForm bagForm) {

        if (bagService.addBag(bagForm)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete-product-in-the-bag")
    public ResponseEntity<?> deleteProductInTheBag(@RequestBody Integer id) {
        bagService.deleteByProductBagId(id);
        return ResponseEntity.ok().build();
    }
}
