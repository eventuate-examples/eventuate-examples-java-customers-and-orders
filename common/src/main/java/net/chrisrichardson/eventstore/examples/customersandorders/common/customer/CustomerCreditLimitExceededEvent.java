package net.chrisrichardson.eventstore.examples.customersandorders.common.customer;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CustomerCreditLimitExceededEvent implements CustomerEvent {
  private String orderId;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  public CustomerCreditLimitExceededEvent() {
  }

  public CustomerCreditLimitExceededEvent(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
