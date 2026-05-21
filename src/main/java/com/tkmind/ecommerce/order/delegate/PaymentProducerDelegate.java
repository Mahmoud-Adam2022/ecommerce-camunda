package com.tkmind.ecommerce.order.delegate;

import com.tkmind.ecommerce.order.entity.Order;
import com.tkmind.ecommerce.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.tkmind.ecommerce.order.model.PaymentRequest;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentProducerDelegate implements JavaDelegate {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepo orderRepo;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String orderId = execution.getBusinessKey();
        PaymentRequest request = new PaymentRequest(orderId,new BigDecimal(100.0));
        kafkaTemplate.send("payment-request", request);
        System.out.println("Payment request sent for order : " + orderId);
        Order order = orderRepo.findById(Integer.valueOf(orderId)).orElseThrow();
        order.setStatus("PAYMENT_PENDING");
        orderRepo.save(order);
    }
}
