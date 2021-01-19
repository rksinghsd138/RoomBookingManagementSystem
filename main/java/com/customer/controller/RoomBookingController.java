package com.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.model.Customer;
import com.customer.service.CustomerService; 

@RestController
public class RoomBookingController {

	@Autowired
	private CustomerService customerService;
	
	//Post call handler -- to register the customer
	@PostMapping("/customer")
	public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer); 
	}
	
	//Get call handler -- to fetch customer details based on customer id
	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long customerId) {
		return customerService.getCustomerById(customerId);
	}
}