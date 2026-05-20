package com.tkmind.ecommerce.order.delegate;

import com.tkmind.ecommerce.order.entity.Order;
import com.tkmind.ecommerce.order.model.InventoryRequest;
import com.tkmind.ecommerce.order.model.InventoryResponse;
import com.tkmind.ecommerce.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ReserveInventoryDelegate implements JavaDelegate {

    private final RestTemplate restTemplate;
    private final OrderRepo orderRepo;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer orderId = Integer.valueOf(execution.getBusinessKey());
        Integer productCode = (Integer)execution.getVariable("productCode");
        Integer quantity = (Integer) execution.getVariable("quantity");
        InventoryRequest inventoryRequest = new InventoryRequest(orderId, productCode, quantity);
        InventoryResponse response =
                restTemplate.postForObject(
                        "http://localhost:8081/api/inventory/release/{resevationId}",
                        inventoryRequest,
                        InventoryResponse.class
                );
        boolean reserved = response != null && response.isReserved();
        execution.setVariable("inventoryReserved", reserved);
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus("INVENTORY_RESERVED");
        orderRepo.save(order);
    }
}
