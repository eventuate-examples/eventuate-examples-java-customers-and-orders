package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.web.customers;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistory.webapi.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain.CustomerViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerOrderHistoryController {

  private final CustomerViewRepository customerViewRepository;

  @Autowired
  public CustomerOrderHistoryController(CustomerViewRepository customerViewRepository) {
    this.customerViewRepository = customerViewRepository;
  }

  @RequestMapping(value="/customers/{customerId}", method= RequestMethod.GET)
  public ResponseEntity<CustomerView> getCustomer(@PathVariable String customerId) {
    return customerViewRepository
            .findById(customerId)
            .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
