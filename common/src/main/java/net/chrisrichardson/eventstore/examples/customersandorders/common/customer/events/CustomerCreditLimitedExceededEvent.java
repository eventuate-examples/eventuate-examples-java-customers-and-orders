package net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events;

import net.chrisrichardson.eventstore.EntityIdentifier;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerCreditLimitedExceededEvent implements CustomerEvent {
  private EntityIdentifier orderId;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public CustomerCreditLimitedExceededEvent() {
  }

  public CustomerCreditLimitedExceededEvent(EntityIdentifier orderId) {
    this.orderId = orderId;
  }

  public EntityIdentifier getOrderId() {
    return orderId;
  }
}
