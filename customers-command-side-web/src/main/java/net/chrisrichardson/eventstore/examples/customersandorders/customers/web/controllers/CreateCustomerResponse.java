package net.chrisrichardson.eventstore.examples.customersandorders.customers.web.controllers;

import net.chrisrichardson.eventstore.EntityIdentifier;

public class CreateCustomerResponse {
  private String customerId;

  public CreateCustomerResponse() {
  }

  public CreateCustomerResponse(EntityIdentifier customerId) {
    this.customerId = customerId.getId();

  }

  public String getCustomerId() {
    return customerId;
  }
}
