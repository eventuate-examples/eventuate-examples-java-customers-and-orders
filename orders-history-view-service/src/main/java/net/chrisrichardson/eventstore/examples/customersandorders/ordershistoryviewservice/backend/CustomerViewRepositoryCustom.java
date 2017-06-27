package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;

public interface CustomerViewRepositoryCustom {

  void addCustomer(String customerId, String customerName, Money creditLimit);

  void addOrder(String customerId, String orderId, Money orderTotal);

  void updateOrderState(String customerId, String orderId, OrderState state);
}
