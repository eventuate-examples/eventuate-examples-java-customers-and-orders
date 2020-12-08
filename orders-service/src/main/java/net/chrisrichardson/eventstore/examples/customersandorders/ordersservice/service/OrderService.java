package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service;

import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.domain.Order;

public interface OrderService {

    EntityWithIdAndVersion<Order> createOrder(String customerId, Money orderTotal);
}
