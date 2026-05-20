package com.tkmind.ecommerce.order.service;

import com.tkmind.ecommerce.order.entity.Order;
import com.tkmind.ecommerce.order.model.OrderRequest;
import com.tkmind.ecommerce.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final RuntimeService runtimeService;
    private final OrderRepo orderRepo;

    public String saveOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setStatus("created");
        orderRepo.save(order);

        Map<String, Object> variables = new HashMap<>();
        variables.put("productCode", orderRequest.getProductCode());
        variables.put("quantity", orderRequest.getQuantity());

        runtimeService.startProcessInstanceByKey(
                "order-process",
                order.getId().toString(),
                variables
        );
        return "Order Created : " + order.getId();
    }
}
