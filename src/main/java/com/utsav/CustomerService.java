package com.utsav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.Stripe;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	public static String key = "put your stripe api key here";
	public List<Customer> getAllCustomers() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
		Stripe.apiKey = key;
		List<Customer> cus = new ArrayList<>();
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("limit", "10");
		CustomerCollection col = Customer.list(customerParams);
		cus = col.getData();
		System.out.println(cus.get(0).getId());
		return cus;
	}

	public Customer addCustomer(SCustomer cus) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("email",cus.getEmail());
		Customer customer = Customer.create(customerParams);
		return customer;
	}
	
	public String deleteCustomer(String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		Customer cu = Customer.retrieve(cid);
		if(cu !=null) {
			cu.delete();
			return "deleted";
		}else {
			return "no such customer";
		}
		
	}

	public String retrieveCustomer(String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		// TODO Auto-generated method stub
		Customer c = Customer.retrieve(cid);
		if(c != null)
			return c.toString();
		else
			return "no such customer";
	}

	public String updateCustomer(String cid, SCustomer cus) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		// TODO Auto-generated method stub
		Stripe.apiKey = key;
		Customer cu = Customer.retrieve(cid);
		if(cu !=null) {
			Map<String, Object> updateParams = new HashMap<String, Object>();
			updateParams.put("email",cus.getEmail());
			cu.update(updateParams);
			return cu.toString();
		}else {
			return "no such customer";
		}
	}
}
