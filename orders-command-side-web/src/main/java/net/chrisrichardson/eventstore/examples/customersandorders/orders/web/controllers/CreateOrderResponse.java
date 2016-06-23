package net.chrisrichardson.eventstore.examples.customersandorders.orders.web.controllers;

import net.chrisrichardson.eventstore.EntityIdentifier;

public class CreateOrderResponse {
  private String orderId;

  public CreateOrderResponse() {
  }

  public CreateOrderResponse(EntityIdentifier orderId) {
    this.orderId = orderId.getId();

  }

  public String getOrderId() {
    return orderId;
  }
}
