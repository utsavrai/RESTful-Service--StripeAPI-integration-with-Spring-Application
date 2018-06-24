###RESTful Service - A Spring application to manage Customers, Plans and Subscriptions without using Stripe&#39;s Dashboard

This spring application uses stripe api which is a technology company. Its software allows individuals and businesses to receive payments over the Internet. Stripe provides APIs that web developers can use to integrate payment processing into their websites and mobile applications. I have used stripe api with spring application which gives the user capabilities of managing customer, plans and subscription. Each of these entities have their respective controllers and services.

Following is an example of subscription plan that I used
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/stateMachine.PNG)
**Customer Controller**

```java

@RestController

public class CustomerController {

 @Autowired

  private CustomerService customerService;

  @RequestMapping(value=&quot;/customers&quot;, method=RequestMethod.GET)

  public List&lt;Customer&gt; getAllCustomers() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{

   return customerService.getAllCustomers();

  }

  @RequestMapping(value=&quot;/customers/add&quot;,method = RequestMethod.POST)

  public Customer addCustomer(@RequestBody SCustomer cus) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

   return customerService.addCustomer(cus);

  }

  @RequestMapping(value=&quot;/customers/delete/{cid}&quot;,method = RequestMethod.GET)

  public String deleteCustomer(@PathVariable String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

   return customerService.deleteCustomer(cid);

  }

  @RequestMapping(value=&quot;/customers/retrieve/{cid}&quot;,method = RequestMethod.GET)

  public String retrieveCustomer(@PathVariable String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

   return customerService.retrieveCustomer(cid);

  }

  @RequestMapping(value=&quot;/customers/update/{cid}&quot;,method = RequestMethod.PUT)

  public String updateCustomer(@PathVariable String cid,@RequestBody SCustomer cus) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

   return customerService.updateCustomer(cid,cus);

  }

}

```

**Example: Adding customer to stripe account using postman**

First add Headers
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/SpringStripe3.1.PNG)
Make a post request at localhost:8080/customers/add
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/SpringStripe3.2.PNG)
Example: Get all customers
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/SpringStripe2.PNG)

every request made at the endpoint of /customer is handled by customer controller which maps each request to their respective controller function which extract path variables if present and call respective function in customer services. This controller allows to major crud operation supported by stripe api.

**Plan Controller**

```java

@RestController

public class PlanController {

 @Autowired

 PlanService planService;

 @RequestMapping(value=&quot;/plans&quot;, method=RequestMethod.GET)

 public List&lt;Plan&gt; getAllPlans() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{

  return planService.getAllPlans();

 }

 @RequestMapping(value=&quot;/plans/add&quot;,method = RequestMethod.POST)

 public String addPlan(@RequestBody SPlan sp) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

  return planService.addPlan(sp);

 }

 @RequestMapping(value=&quot;plans/delete/{pid}&quot;,method = RequestMethod.GET)

 public String deletePlan(@PathVariable String pid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

  return planService.deletePlan(pid);

 }

}

```

Example: Get all Plans
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/Spring_StripeAPI4.PNG)
stripe plans are at the heart of subscriptions, establishing the billing cycle, currency, and base cost. Every plan is attached to a product, which represents the application or service offered to customers. Products can have more than one plan, reflecting variations in price and duration—–such as monthly and annual pricing at different rates. This controller allows to create plans, retrieve all plans and delete plan.

**Subscription Controller**

```java

@RestController

public class SubscriptionController {

 @Autowired

 private SubscriptionService subscriptionService;

 @RequestMapping(value=&quot;/subscriptions&quot;, method=RequestMethod.GET)

 public List&lt;Subscription&gt; getAllCustomers() throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{

  return subscriptionService.getAllSubscriptions();

  //return subscriptionService.getAllSubscriptions();

 }

 @RequestMapping(value=&quot;/subscriptions/add&quot;, method=RequestMethod.POST)

 public String addSubscription(@RequestBody SSubscription sub) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{

  return subscriptionService.addSubscription(sub);

  //return subscriptionService.getAllSubscriptions();

 }

 @RequestMapping(value = &quot;/subscriptions/cancel/{cid}&quot;,method=RequestMethod.GET)

 public String cancelSub(@PathVariable String cid) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {

  return subscriptionService.cancelSub(cid);

 }

}

```

Example: Get all subscription
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/SpringStripe1.PNG)
Subscriptions allow you to charge a customer on a recurring basis. A subscription ties a customer to a particular plan you&#39;ve created.

In this example I have used four subscriptions - zero$, zero$\_30day\_freeTrial, 99$, 99$\_30day\_freeTrial

The purpose of zero$ plan is to avoid any unethical way of subscribing to any other free trials, it does by checking if a user is in under zero$ plan then the user might have either canceled or his/her subscription must have ended. This ensures that the only way out from this plan is to subscribe to 99$ plan which means that the user have to pay, no options left!

when the user signs up, he/she is subscribed to the zero$\_30day\_freeTrial, that ensures that the user is under 30-day free trial.

Now in between the user can choose to switch to 99$ plan even though he/she is under free trial. Or the user can cancel its free trial which will switch him to zero$ plan. Once the trial period gets over the user is switched to zero$ plan.

Now suppose the user wants to get back in and wants to extend its trial, or the product is offering one more month of free trial but on one condition that this to avail the offer you have to enter your credit card details which will be secured by stripe, (nothing is stored by the product). With this offer the user is upgraded to 99$\_30day\_freeTrial plan in which the user won&#39;t be charged anything but as soon as the trial ends the user will be switched to 99$ plan and will be charged monthly. Now if the user cancels before the trial then he/she will be switched to zero$ plan which again makes sure that user cannot go back to any free trial plans which is handled at the back end.

Following is the state machine for the subscription
![](https://raw.githubusercontent.com/utsavrai/RESTful-Service--StripeAPI-integration-with-Spring-Application/master/stateMachine.PNG)
