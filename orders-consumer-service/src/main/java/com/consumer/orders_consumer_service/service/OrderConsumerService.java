package com.consumer.orders_consumer_service.service;

import com.salinda.kafka.avro.Order;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumerService {

    private double totalPriceSum = 0.0;
    private int totalOrderCount = 0;

    @KafkaListener(topics = "${application.kafka.topic}", groupId = "orders-group")
    public void consumeOrder(ConsumerRecord<String, Order> record) {
        Order order = record.value();
        double price = order.getPrice();

        if (price > 90.00) {
            System.out.printf(" High Value Order Detect ($%.2f) - SIMULATING CRASH!%n", price);
            throw new RuntimeException("Simulated Database Failure for expensive item!");
        }
        synchronized (this) {
            totalOrderCount++;
            totalPriceSum += price;

            // 3. Calculate Average
            double averagePrice = totalPriceSum / totalOrderCount;


            System.out.printf(" New Order: %s ($%.2f) | Total Orders: %d |  Running Average: $%.2f%n",
                    order.getProduct(), price, totalOrderCount, averagePrice);
        }
    }
}