package com.tkmind.ecommerce.order.delegate;

import com.tkmind.ecommerce.order.entity.Order;
import com.tkmind.ecommerce.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteOrderDelegate implements JavaDelegate {

    private final OrderRepo orderRepo;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer orderId = Integer.valueOf(execution.getBusinessKey());
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus("COMPLETED");
        orderRepo.save(order);
        System.out.println("Order completed : " + orderId);
    }
}
