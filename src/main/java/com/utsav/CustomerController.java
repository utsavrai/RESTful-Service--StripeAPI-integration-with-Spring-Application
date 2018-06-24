package com.utsav;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
@RestController	
public class CustomerController {
	@Autowired
		private CustomerService customerService;
		@RequestMapping(value="/customers", method=RequestMethod.GET)
		public List<Customer> getAllCustomers() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
			return customerService.getAllCustomers();
		}
		
		@RequestMapping(value="/customers/add",method = RequestMethod.POST)
		public Customer addCustomer(@RequestBody SCustomer cus) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
			return customerService.addCustomer(cus);
		}
		
		@RequestMapping(value="/customers/delete/{cid}",method = RequestMethod.GET)
		public String deleteCustomer(@PathVariable String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
			return customerService.deleteCustomer(cid);
		}
		
		@RequestMapping(value="/customers/retrieve/{cid}",method = RequestMethod.GET)
		public String retrieveCustomer(@PathVariable String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
			return customerService.retrieveCustomer(cid);
		}
		
		@RequestMapping(value="/customers/update/{cid}",method = RequestMethod.PUT)
		public String updateCustomer(@PathVariable String cid,@RequestBody SCustomer cus) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
			return customerService.updateCustomer(cid,cus);
		}
}
