package com.utsav;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Plan;
import com.stripe.model.PlanCollection;

@Service
public class PlanService {
	public static String key = "put your stripe api key here";
	public List<Plan> getAllPlans() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		// TODO Auto-generated method stub
		Stripe.apiKey = key;
		Map<String, Object> planParams = new HashMap<String, Object>();
		planParams.put("limit", "10");

		PlanCollection plans = Plan.list(planParams);
		List<Plan> p = plans.getData();
		return p;
		//return null;
	}
	
	public String addPlan(SPlan sp) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		Stripe.apiKey = key;
		String curr = sp.getCurr();
		String intr = sp.getInterval();
		String prod = sp.getProdId();
		String nick = sp.getNickname();
		int amt = sp.getAmt();
		int days = sp.getDays();
		
		Map<String, Object> params = new HashMap<>();
		params.put("currency", curr);
		params.put("interval", intr);
		params.put("product", prod);
		params.put("nickname", nick);
		params.put("amount", amt);
		params.put("trial_period_days", days);
		Plan plan = Plan.create(params);
		return plan.toString();
	}

	public String deletePlan(String pid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		// TODO Auto-generated method stub
		Stripe.apiKey = key;
		if(Plan.retrieve(pid)!=null) {
			Plan plan = Plan.retrieve(pid);
			plan.delete();
			return "deleted";
		}else {
			return "no such plan";
		}
	}

}
