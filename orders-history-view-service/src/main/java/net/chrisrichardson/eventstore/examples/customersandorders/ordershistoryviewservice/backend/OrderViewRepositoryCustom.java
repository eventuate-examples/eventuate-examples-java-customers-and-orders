package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;

public interface OrderViewRepositoryCustom {
  void addOrder(String orderId, Money orderTotal);
  void updateOrderState(String orderId, OrderState state);
}
