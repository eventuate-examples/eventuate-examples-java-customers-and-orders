package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import rx.Observable;

public interface CustomerService {

  Observable<EntityWithIdAndVersion<Customer>> createCustomer(String name, Money creditLimit);
}
