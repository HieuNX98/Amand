package com.amand.service;

import com.amand.dto.OrderDto;
import com.amand.form.OrderForm;

public interface IOrderService {
    OrderDto save (OrderForm orderForm);
}
