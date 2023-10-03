package com.amand.api.admin;

import com.amand.ServiceImpl.BagServiceImpl;
import com.amand.Utils.PaymentUtils;
import com.amand.dto.BagDto;
import com.amand.entity.BagEntity;
import com.amand.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/api")
public class PaymentApi {

    @Autowired
    private BagServiceImpl bagService;

    @PostMapping("/create-payment-url")
    public ResponseEntity<?> createUrl(@RequestBody OrderForm orderForm) throws UnsupportedEncodingException {

        BagDto bag = bagService.findOneById(orderForm.getBagId());
        String url = PaymentUtils.createURL(bag.getTotalPrice(), String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(url);
    }
}
