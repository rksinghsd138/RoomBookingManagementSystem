package com.customer.RoomBooking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.customer.controller.RoomBookingController;
import com.customer.model.Customer;
import com.customer.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RoomBookingController.class)
@WithMockUser
public class RoomBookingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Autowired
	private CustomerService customerService;
	
	Customer mockCustomer = new Customer(101L, "userFirstName", "userLastName", "userEmail", "userPassword");

	String testCustomerDetails = "{\"id\":1,\"firstName\":\"userFirstName\",\"lastName\":\"userLastName\",\"userName\":\"user@gmail.com\",\"password\":\"user@12345\"}";
	
	@Test
	public void getCustomerById() throws Exception {
		
		ResponseEntity<?> responseEntity = new ResponseEntity<>(mockCustomer, HttpStatus.OK);

		//Mockito.when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(responseEntity);
		
		Mockito.when(restTemplate.exchange(
				 Matchers.anyString(),
				 Matchers.any(HttpMethod.class),
				 Matchers.<HttpEntity<?>> any(), 
				 Matchers.<Class<Customer>> any())).thenReturn((ResponseEntity<Customer>) responseEntity);
		
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/id").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{id:1,firstName:userFirstName,lastName:userLastName,userName:userEmailid,password:userPassword}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void saveCustomer() throws Exception {
		ResponseEntity<?> responseEntity = new ResponseEntity<>(mockCustomer, HttpStatus.OK);

		Mockito.when(restTemplate.exchange(
				 Matchers.anyString(),
				 Matchers.any(HttpMethod.class),
				 Matchers.<HttpEntity<?>> any(), 
				 Matchers.<Class<Customer>> any())).thenReturn((ResponseEntity<Customer>) responseEntity);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/customer")
				.accept(MediaType.APPLICATION_JSON).content(testCustomerDetails)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}	
}