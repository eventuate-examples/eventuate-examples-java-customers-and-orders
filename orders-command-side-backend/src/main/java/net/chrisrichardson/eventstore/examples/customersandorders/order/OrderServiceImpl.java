package net.chrisrichardson.eventstore.examples.customersandorders.order;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.repository.AggregateRepository;
import rx.Observable;

public class OrderServiceImpl implements OrderService {

  private final AggregateRepository<Order, OrderCommand> orderRepository;

  public OrderServiceImpl(AggregateRepository<Order, OrderCommand> orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public Observable<EntityWithIdAndVersion<Order>>
        createOrder(EntityIdentifier customerId, Money orderTotal) {
    return orderRepository.save(new CreateOrderCommand(customerId, orderTotal));
  }
}
