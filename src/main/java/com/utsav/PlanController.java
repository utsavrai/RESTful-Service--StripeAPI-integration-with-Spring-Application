package com.utsav;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Plan;

@RestController
public class PlanController {
	@Autowired
	PlanService planService;
	
	@RequestMapping(value="/plans", method=RequestMethod.GET)
	public List<Plan> getAllPlans() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
		return planService.getAllPlans();
	}
	
	@RequestMapping(value="/plans/add",method = RequestMethod.POST)
	public String addPlan(@RequestBody SPlan sp) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		return planService.addPlan(sp);
		
	}
	
	@RequestMapping(value="plans/delete/{pid}",method = RequestMethod.GET)
	public String deletePlan(@PathVariable String pid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
		return planService.deletePlan(pid);
		
	}
}
