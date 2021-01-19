package com.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.customer.model.Customer;
import com.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public ResponseEntity<?> saveCustomer(Customer customer) {
		try {
			// Before saving, first check if customer details is already available for the given email id
			Customer isCustomerAvailable = customerRepository.findCustomerByEmail(customer.getUserName());
			if (null != isCustomerAvailable) {
				// if user already registered, send customer details with response code 400
				return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST); //400
			} else {
				// Before saving customer details, check for the required password length
				// we can write custom validate logic here
				// For now have written simple validation logic here to check password length
				if (null != customer.getPassword() && customer.getPassword().length()>=8 && customer.getPassword().length()<=10) {
					// register customer and send details with response code 201
					customerRepository.save(customer);
					return new ResponseEntity<>(customer, HttpStatus.CREATED);//201
				} else {
					return new ResponseEntity<>("Please provide the valid password", HttpStatus.BAD_REQUEST);//400
				}
			}
		} catch (Exception e) {
			// we can also use logging mechanism here
			e.printStackTrace();
		}
		// in case of exception in try block, send below error message with response code 400
		return new ResponseEntity<>("Error while saving customer details", HttpStatus.BAD_REQUEST);//400
	}

	// In requirement docs, it is not clear that what will be the customer id, is it email id or something else.
	// So I have given id, we can change it as per our requirement.
	public ResponseEntity<?> getCustomerById(Long customerId) {
		try {
			// check for the customer details using customer id
			Optional<Customer> customer = customerRepository.findById(customerId);
			if (customer.isPresent()) {
				// if customer details available in DB, send customer details with response code 200
				return new ResponseEntity<>(customer, HttpStatus.OK);//200
			} else {
				// otherwise send below error message with response code 404
				return new ResponseEntity<>("User not found for the given user ID", HttpStatus.NOT_FOUND);//404
			}
		} catch (Exception e) {
			// we can also use logging mechanism here
			e.printStackTrace();
		}
		// in case of exception in try block, send below error message with response code 400
		return new ResponseEntity<>("Error while fetching customer details", HttpStatus.BAD_REQUEST);//400
	}
}