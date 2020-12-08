package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.service;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.CreateCustomerCommand;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.CustomerCommand;

public class CustomerServiceImpl implements CustomerService {

  private final AggregateRepository<Customer, CustomerCommand> customerRepository;

  public CustomerServiceImpl(AggregateRepository<Customer, CustomerCommand> customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public EntityWithIdAndVersion<Customer> createCustomer(String name, Money creditLimit) {
    return customerRepository.save(new CreateCustomerCommand(name, creditLimit));
  }

  @Override
  public EntityWithMetadata<Customer> findById(String customerId) {
    return customerRepository.find(customerId);
  }
}
