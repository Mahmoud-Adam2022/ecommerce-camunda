package com.tkmind.ecommerce.order.controller;

import com.tkmind.ecommerce.order.model.OrderRequest;
import com.tkmind.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest){
        return orderService.saveOrder(orderRequest);
    }



}
