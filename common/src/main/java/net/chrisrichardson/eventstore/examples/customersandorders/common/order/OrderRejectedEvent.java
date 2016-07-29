package net.chrisrichardson.eventstore.examples.customersandorders.common.order;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderRejectedEvent implements OrderEvent {

  private String customerId;

  private OrderRejectedEvent() {
  }

  public OrderRejectedEvent(String customerId) {
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

  public String getCustomerId() {
    return customerId;
  }
}
