package com.tkmind.ecommerce.order.kafka;

import com.tkmind.ecommerce.order.model.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaPaymentConsumer {

    private final RuntimeService runtimeService;

    @KafkaListener(topics = "payment-response", groupId = "order-group")
    public void consume(PaymentResponse paymentResponse) {
        runtimeService
                .createMessageCorrelation("PAYMENT_RESPONSE")
                .processInstanceBusinessKey(String.valueOf(paymentResponse.getOrderId()))
                .setVariable("paymentSuccess", paymentResponse.getSuccess())
                .correlate();

        System.out.println("Payment response correlated : " + paymentResponse.getOrderId());
    }
}
