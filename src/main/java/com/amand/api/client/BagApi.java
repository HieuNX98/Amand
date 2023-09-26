package com.amand.api.client;

import com.amand.dto.BagDto;
import com.amand.form.BagForm;
import com.amand.service.IBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        BagDto bagDto = bagService.addBag(bagForm);
        if (bagDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bagDto);
    }
}
