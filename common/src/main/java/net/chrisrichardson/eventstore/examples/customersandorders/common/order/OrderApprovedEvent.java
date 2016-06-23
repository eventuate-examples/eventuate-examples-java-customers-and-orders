package net.chrisrichardson.eventstore.examples.customersandorders.common.order;

import net.chrisrichardson.eventstore.EntityIdentifier;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderApprovedEvent implements OrderEvent {

  private EntityIdentifier customerId;

  private OrderApprovedEvent() {
  }

  public OrderApprovedEvent(EntityIdentifier customerId) {
    this.customerId = customerId;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public EntityIdentifier getCustomerId() {
    return customerId;
  }
}
