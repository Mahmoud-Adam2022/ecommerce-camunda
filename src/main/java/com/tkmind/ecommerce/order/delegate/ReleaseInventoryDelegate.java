package com.tkmind.ecommerce.order.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReleaseInventoryDelegate implements JavaDelegate {

    private final RestTemplate restTemplate;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String orderId = execution.getBusinessKey();
        restTemplate.postForObject(
                "http://localhost:8082/inventory/release",
                Map.of("orderId", Integer.valueOf(orderId)),
                Void.class
        );
        System.out.println("Inventory released : " + orderId);
    }
}
