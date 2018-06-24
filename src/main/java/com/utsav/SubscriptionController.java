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
import com.stripe.model.Subscription;
@RestController	
public class SubscriptionController {
	@Autowired
	private SubscriptionService subscriptionService;
	@RequestMapping(value="/subscriptions", method=RequestMethod.GET)
	public List<Subscription> getAllCustomers() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
		return subscriptionService.getAllSubscriptions();
		//return subscriptionService.getAllSubscriptions();
	}
	
	@RequestMapping(value="/subscriptions/add", method=RequestMethod.POST)
	public String addSubscription(@RequestBody SSubscription sub) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
		return subscriptionService.addSubscription(sub);
		//return subscriptionService.getAllSubscriptions();
	}
	
	@RequestMapping(value = "/subscriptions/cancel/{cid}",method=RequestMethod.GET)
	public String cancelSub(@PathVariable String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		return subscriptionService.cancelSub(cid);
	}
}
