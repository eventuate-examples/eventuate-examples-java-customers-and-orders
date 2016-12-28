package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.concurrent.CompletableFuture;

public class CustomerServiceImpl implements CustomerService {

  private final AggregateRepository<Customer, CustomerCommand> customerRepository;

  public CustomerServiceImpl(AggregateRepository<Customer, CustomerCommand> customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public CompletableFuture<EntityWithIdAndVersion<Customer>> createCustomer(String name, Money creditLimit) {
    return customerRepository.save(new CreateCustomerCommand(name, creditLimit));
  }
}
