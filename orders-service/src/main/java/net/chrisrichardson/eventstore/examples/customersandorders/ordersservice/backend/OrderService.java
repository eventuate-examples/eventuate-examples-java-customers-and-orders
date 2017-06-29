package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

public interface OrderService {

    EntityWithIdAndVersion<Order> createOrder(String customerId, Money orderTotal);
}
