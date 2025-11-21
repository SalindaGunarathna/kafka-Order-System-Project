package com.producer.orders_producer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class OrdersProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersProducerServiceApplication.class, args);
	}

}
