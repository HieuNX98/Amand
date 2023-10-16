package com.amand.api.client;

import com.amand.constant.SystemConstant;
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

    @PostMapping("/confirmOrder")
    public ResponseEntity<?> confirmOrder(@RequestBody Integer orderId) {
        if (orderService.updateStatusById(orderId, SystemConstant.ACTIVE_STATUS)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
