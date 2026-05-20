package com.tkmind.ecommerce.order.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic myTopic() {
        return TopicBuilder
                .name("payment-request")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentResponseTopic() {

        return TopicBuilder
                .name("payment-response")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
