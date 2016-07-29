package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.concurrent.CompletableFuture;

public interface CustomerService {

  CompletableFuture<EntityWithIdAndVersion<Customer>> createCustomer(String name, Money creditLimit);
}
