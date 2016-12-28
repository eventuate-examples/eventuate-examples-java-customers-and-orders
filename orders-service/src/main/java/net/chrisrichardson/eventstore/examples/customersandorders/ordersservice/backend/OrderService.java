package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.concurrent.CompletableFuture;

public interface OrderService {

    CompletableFuture<EntityWithIdAndVersion<Order>> createOrder(String customerId, Money orderTotal);
}
