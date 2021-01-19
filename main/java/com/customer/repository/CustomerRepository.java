package com.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	 //Have written custom method to fetch customer details based on email id
	 @Query(value = "SELECT * FROM customer WHERE email = ?1", nativeQuery = true)
	 Customer findCustomerByEmail(String customerEmail);
}
