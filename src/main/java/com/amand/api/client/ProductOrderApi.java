package com.amand.api.client;

import com.amand.constant.SystemConstant;
import com.amand.dto.OrderDto;
import com.amand.dto.ProductOrderDto;
import com.amand.entity.OrderEntity;
import com.amand.entity.ProductEntity;
import com.amand.entity.ProductOrderEntity;
import com.amand.service.IOrderService;
import com.amand.service.IProductOrderService;
import com.amand.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductOrderApi {

    @Autowired
    private IProductOrderService productOrderService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService orderService;

    @PutMapping("/huy-don-hang")
    public ResponseEntity<?> cancelOrder(@RequestBody Integer orderId) {
        List<ProductOrderEntity> productOrderEntities = productOrderService.findByOrderId(orderId, SystemConstant.INACTIVE_STATUS);
        if (CollectionUtils.isEmpty(productOrderEntities)) {
            return ResponseEntity.badRequest().build();
        }
        for (ProductOrderEntity productOrderEntity : productOrderEntities) {
            ProductEntity productEntity = productService.findById(productOrderEntity.getProduct().getId());
            productEntity.setAmount(productEntity.getAmount() + productOrderEntity.getAmount());
            productService.save(productEntity);
        }
        orderService.updateStatusById(orderId, SystemConstant.CANCEL_STATUS);
        return ResponseEntity.ok().build();
    }
}
