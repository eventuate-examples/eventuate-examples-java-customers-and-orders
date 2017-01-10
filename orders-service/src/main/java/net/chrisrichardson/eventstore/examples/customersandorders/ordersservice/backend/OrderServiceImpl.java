package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.concurrent.CompletableFuture;

public class OrderServiceImpl implements OrderService {

  private final AggregateRepository<Order, OrderCommand> orderRepository;

  public OrderServiceImpl(AggregateRepository<Order, OrderCommand> orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public CompletableFuture<EntityWithIdAndVersion<Order>>
        createOrder(String customerId, Money orderTotal) {
    return orderRepository.save(new CreateOrderCommand(customerId, orderTotal));
  }
}
