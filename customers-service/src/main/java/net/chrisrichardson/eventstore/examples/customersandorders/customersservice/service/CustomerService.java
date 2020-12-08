package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.service;

import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EntityWithMetadata;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.Customer;

public interface CustomerService {

  EntityWithIdAndVersion<Customer> createCustomer(String name, Money creditLimit);

  EntityWithMetadata<Customer> findById(String customerId);
}
