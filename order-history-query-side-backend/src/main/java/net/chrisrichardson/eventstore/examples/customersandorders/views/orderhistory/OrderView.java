package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OrderView {

  @Id
  private String id;

  @Version
  private Long version;

  private OrderState state;
  private Money orderTotal;


  public OrderView() {
  }

  public OrderView(String id, Money orderTotal) {
    this.id = id;
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
