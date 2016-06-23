package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class CustomerView {

  @Id
  private String id;

  @Version
  private long version;


  private Map<String, OrderInfo> orders = new HashMap<>();
  private String name;
  private Money creditLimit;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void addOrder(String orderId, Money orderTotal) {
    orders.put(orderId, new OrderInfo(orderId, orderTotal));
  }

  public void approveOrder(String orderId) {
    orders.get(orderId).approve();
  }

  public void rejectOrder(String orderId) {
    orders.get(orderId).reject();
  }

  public Map<String, OrderInfo> getOrders() {
    return orders;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setCreditLimit(Money creditLimit) {
    this.creditLimit = creditLimit;
  }

  public Money getCreditLimit() {
    return creditLimit;
  }
}
