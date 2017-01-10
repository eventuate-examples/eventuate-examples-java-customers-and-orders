package net.chrisrichardson.eventstore.examples.customersandorders.common.customer;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerCreditReservedEvent implements CustomerEvent {
  private String orderId;
  private Money orderTotal;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public CustomerCreditReservedEvent() {
  }

  public CustomerCreditReservedEvent(String orderId, Money orderTotal) {
    this.orderId = orderId;
    this.orderTotal = orderTotal;
  }

  public String getOrderId() {
    return orderId;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }
}
