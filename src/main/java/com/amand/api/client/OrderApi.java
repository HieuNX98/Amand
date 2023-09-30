package com.amand.api.client;

import com.amand.dto.OrderDto;
import com.amand.form.OrderForm;
import com.amand.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class OrderApi {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderForm orderForm) {
        OrderDto orderDto = orderService.save(orderForm);
        return ResponseEntity.ok(orderDto);
    }
}
