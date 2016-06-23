package net.chrisrichardson.eventstore.examples.customersandorders.order;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import rx.Observable;

public interface OrderService {

  Observable<EntityWithIdAndVersion<Order>> createOrder(EntityIdentifier customerId, Money orderTotal);
}
