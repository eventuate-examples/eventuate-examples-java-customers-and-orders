package net.chrisrichardson.eventstore.examples.customersandorders.customers.webapi;


public class CreateCustomerResponse {
  private String customerId;

  public CreateCustomerResponse() {
  }

  public CreateCustomerResponse(String customerId) {
    this.customerId = customerId;

  }

  public String getCustomerId() {
    return customerId;
  }
}
