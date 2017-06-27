package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.web;

import net.chrisrichardson.eventstore.examples.customersandorders.customerscommon.CreateCustomerRequest;
import net.chrisrichardson.eventstore.examples.customersandorders.customerscommon.CreateCustomerResponse;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class CustomerController {

  private CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping(value = "/customers", method = RequestMethod.POST)
  public CompletableFuture<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
    return customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCreditLimit())
            .thenApply(ewidv -> new CreateCustomerResponse(ewidv.getEntityId()));
  }
}
