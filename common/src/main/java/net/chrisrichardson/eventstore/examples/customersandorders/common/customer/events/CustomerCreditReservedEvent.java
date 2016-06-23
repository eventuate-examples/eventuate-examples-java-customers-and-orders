package net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerCreditReservedEvent implements CustomerEvent {
  private EntityIdentifier orderId;
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

  public CustomerCreditReservedEvent(EntityIdentifier orderId, Money orderTotal) {
    this.orderId = orderId;
    this.orderTotal = orderTotal;
  }

  public EntityIdentifier getOrderId() {
    return orderId;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }
}
