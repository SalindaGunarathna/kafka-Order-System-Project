package com.consumer.orders_consumer_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> template) {
        // 1. Define the Recoverer (Moves failed messages to the DLQ topic)
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template);

        FixedBackOff backoff = new FixedBackOff(1000L, 3L);

        // 3. Create the Handler
        return new DefaultErrorHandler(recoverer, backoff);
    }
}