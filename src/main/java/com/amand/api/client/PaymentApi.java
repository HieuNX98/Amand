package com.amand.api.client;

import com.amand.Utils.PaymentUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.ApiResponse;
import com.amand.dto.BagDto;
import com.amand.form.OrderForm;
import com.amand.service.IBagService;
import com.amand.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class PaymentApi {
    @Autowired
    private IBagService bagService;

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create-payment-bag-url")
    public ResponseEntity<?> createPaymentBagUrl(@Validated @RequestBody OrderForm orderForm, BindingResult result, HttpSession session)
            throws UnsupportedEncodingException {
        session.setAttribute("orderForm", orderForm);
        Map<String, String> responseMsg = orderService.validatePay(result);
        if (CollectionUtils.isEmpty(responseMsg)) {
            BagDto bagDto = bagService.findOneById(orderForm.getBagId());
            if (bagDto == null) {
                return ResponseEntity.badRequest().build();
            }
            String url = PaymentUtils.createURL(bagDto.getTotalPrice(), String.valueOf(System.currentTimeMillis()));
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(url));
            return ResponseEntity.ok(response);
        }
        ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, responseMsg);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/create-payment-url")
    public ResponseEntity<?> createPaymentUrl(@Validated @RequestBody OrderForm orderForm, BindingResult result, HttpSession session)
            throws UnsupportedEncodingException {
        session.setAttribute("orderForm", orderForm);
        Map<String, String> responseMsg = orderService.validatePay(result);
        if (CollectionUtils.isEmpty(responseMsg)) {
            String url = PaymentUtils.createURL(orderForm.getTotalPrice(), String.valueOf(System.currentTimeMillis()));
            ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_OK, List.of(url));
            return ResponseEntity.ok(response);
        }
        ApiResponse response = new ApiResponse(SystemConstant.API_STATUS_NG, responseMsg);
        return ResponseEntity.ok(response);
    }
}
