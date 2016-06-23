package net.chrisrichardson.eventstore.examples.customersandorders.order;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

public class CreateOrderCommand implements OrderCommand {
  private final EntityIdentifier customerId;
  private final Money orderTotal;

  public CreateOrderCommand(EntityIdentifier customerId, Money orderTotal) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
  }

  public EntityIdentifier getCustomerId() {
    return customerId;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }
}
