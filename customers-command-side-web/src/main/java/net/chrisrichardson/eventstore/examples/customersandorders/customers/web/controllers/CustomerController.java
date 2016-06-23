package net.chrisrichardson.eventstore.examples.customersandorders.customers.web.controllers;

import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.customer.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

@RestController
public class CustomerController {

  private CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping(value="/customers", method= RequestMethod.POST)
  public Observable<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
    Observable<EntityWithIdAndVersion<Customer>> order =
            customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCreditLimit());
    return order.map(ewidv -> new CreateCustomerResponse(ewidv.getEntityIdentifier()));
  }

}
