package net.chrisrichardson.eventstore.examples.customersandorders.ordershistory.webapi;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.OrderState;

public class OrderInfo {

  private OrderState state;
  private String orderId;
  private Money orderTotal;


  public OrderInfo() {
  }

  public OrderInfo(String orderId, Money orderTotal) {
    this.orderId = orderId;
    this.orderTotal = orderTotal;
    this.state = OrderState.CREATED;
  }

  public void approve() {
    state = OrderState.APPROVED;
  }

  public void reject() {
    state = OrderState.REJECTED;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public OrderState getState() {
    return state;
  }
}
