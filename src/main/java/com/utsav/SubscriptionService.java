package com.utsav;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSubscriptionCollection;
import com.stripe.model.Plan;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionCollection;
@Service
public class SubscriptionService {
	private static String key = "put your stripe api key here";
	public List<Subscription> getAllSubscriptions() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limit", 10);
		SubscriptionCollection subscriptions = Subscription.list(params);
		List<Subscription> list = subscriptions.getData();
		return list;
	}
	
	public String addSubscription(SSubscription sub) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		Subscription subscription;
		String priority = "";
		String planId = sub.getPlanId();
		String cust = sub.getCustId();
		Customer c = Customer.retrieve(cust);

		CustomerSubscriptionCollection str = c.getSubscriptions();
		List<Subscription> list = str.getData();

		// put your logic here for subscribing the user
		//here is my example which is used in one of the product where i worked in past.
		if(list.size() != 0){
			
			Subscription current_subs = Subscription.retrieve(list.get(0).getId());
			Plan current_plan= list.get(0).getPlan();
			String current_pid = current_plan.getId();
			
			if(current_pid.equals("zero$")) { // currently at zero$
				if(planId.equals("ninety$")) { // can be subscribed to ninety$ plan
					current_subs.cancel(null);
					Subscription s = subscribeUser(sub);
					return s.toString();
				}else {
					return "Sorry, that ain't happenin'";
				}
			}else if(current_pid.equals("zero$_30")) { //currently at zero$_30
				if(planId.equals("ninety$")) { // can be subscribed to ninety$ plan
					current_subs.cancel(null);
					Subscription s = subscribeUser(sub);
					return s.toString();
				}else if(planId.equals("ninety$_30")){
					return "Unlock another 15 day trial by adding your card, thank you";
					//after verifying the token or adding the card subscribe him by calling
					//subscribeUser(sub) but after cancelling current subscription
				}else {
					return "Cannot subscribe you to any other plans, sorry";
				}
			}else if(current_pid.equals("ninety_30$")) { //currently at ninety_30$
				//check if trial ended if yes cancel this subscription and move him by subscribing him to 99$
				return "cannot be subscribed to any other plan from here except for cancelling";
			}else if(current_pid.equals("ninety$")) { //currently at ninety$
				//can only be canceled from here, cancel this subscription and move to zero$ plan
				return "cannot be subscribed to any other plan from here except for cancelling";
			}else {
				return "no plan found";
			}
		}else {
			if(planId.equals("zero$_30") || planId.equals("ninety$")) {
				Subscription s = subscribeUser(sub);
				return s.toString();
			}else {
				return "Sorry, first subscription can only be zero$_30 and ninety$";
			}
		}
		
		
		
	}

	public String cancelSub(String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		String cust = cid;
		Customer c = Customer.retrieve(cust);
		//System.out.println(c.toString());
		CustomerSubscriptionCollection str = c.getSubscriptions();
		List<Subscription> list = str.getData();
		if(list.size() != 0){
			Subscription sub = Subscription.retrieve(list.get(0).getId());
			Plan plan= list.get(0).getPlan();
			String planId = plan.getId();
			Map<String, Object> updateParams = new HashMap<String, Object>();
			updateParams.put("description", planId);
			c.update(updateParams);
			sub.cancel(null);
			return "canceled";
		}else {
			return "no subscription for this customer";
		}
	}
	
	
	private Subscription subscribeUser(SSubscription sub) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		Subscription subscription;
		String planId = sub.getPlanId();
		String cust = sub.getCustId();
		Customer c = Customer.retrieve(cust);
		Map<String, Object> item = new HashMap<>();
		item.put("plan", planId);
		Map<String, Object> items = new HashMap<>();
		items.put("0", item);
		Map<String, Object> params = new HashMap<>();
		params.put("customer", cust);
		params.put("items", items);
		DateFormat formatter;
		formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		Date date_temp=null;
	    long unixTime = 0;
	    formatter.setTimeZone(TimeZone.getTimeZone("UTC")); //Specify your timezone
	    try {
	    	
	    	date_temp = (Date) formatter.parse(sub.getTrialEnd());
	    	unixTime = date_temp.getTime();
	    	unixTime = unixTime / 1000;
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	   
		params.put("trial_end", unixTime);
		subscription = Subscription.create(params);
		return subscription;
	}

}
