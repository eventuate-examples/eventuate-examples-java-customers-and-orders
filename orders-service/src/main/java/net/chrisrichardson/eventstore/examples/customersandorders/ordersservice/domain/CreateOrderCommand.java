package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.domain;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.OrderCommand;

public class CreateOrderCommand implements OrderCommand {
  private final String customerId;
  private final Money orderTotal;

  public CreateOrderCommand(String customerId, Money orderTotal) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
  }

  public String getCustomerId() {
    return customerId;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }
}
