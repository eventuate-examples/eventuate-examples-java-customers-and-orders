package net.chrisrichardson.eventstore.examples.customersandorders.customerscommon;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

public class GetCustomerResponse {
  private String customerId;
  private Money creditLimit;
  private Money money;

  public GetCustomerResponse() {
  }

  public GetCustomerResponse(String customerId, Money creditLimit, Money money) {
    this.customerId = customerId;
    this.creditLimit = creditLimit;
    this.money = money;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Money getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(Money creditLimit) {
    this.creditLimit = creditLimit;
  }

  public Money getMoney() {
    return money;
  }

  public void setMoney(Money money) {
    this.money = money;
  }
}
