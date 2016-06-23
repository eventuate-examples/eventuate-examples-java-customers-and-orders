package net.chrisrichardson.eventstore.examples.customersandorders.common.order;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderCreatedEvent implements OrderEvent {
  private Money orderTotal;
  private EntityIdentifier customerId;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public OrderCreatedEvent() {
  }

  public OrderCreatedEvent(EntityIdentifier customerId, Money orderTotal) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public EntityIdentifier getCustomerId() {
    return customerId;
  }
}
