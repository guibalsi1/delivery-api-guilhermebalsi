package com.deliverytech.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryApiApplication {

	public static void main(String[] args) {
		System.out.println("Delivery API Application started");
		SpringApplication.run(DeliveryApiApplication.class, args);
	}

}
