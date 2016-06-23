package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

public class ReserveCreditCommand implements CustomerCommand {
  private final Money orderTotal;
  private final EntityIdentifier orderId;

  public ReserveCreditCommand(Money orderTotal, EntityIdentifier orderId) {
    this.orderTotal = orderTotal;
    this.orderId = orderId;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public EntityIdentifier getOrderId() {
    return orderId;
  }
}
