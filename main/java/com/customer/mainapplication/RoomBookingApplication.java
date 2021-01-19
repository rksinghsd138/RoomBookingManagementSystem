package com.customer.mainapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.customer.controller", "com.customer.service"})
@EntityScan("com.customer.model")
@EnableJpaRepositories("com.customer.repository")
public class RoomBookingApplication {
	
	//main method to start spring boot application
	public static void main(String[] args) {
		SpringApplication.run(RoomBookingApplication.class, args);
	}
}