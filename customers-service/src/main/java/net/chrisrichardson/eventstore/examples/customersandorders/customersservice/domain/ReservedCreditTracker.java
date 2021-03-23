package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.HashMap;
import java.util.Map;

public class ReservedCreditTracker {
  private Map<String, Money> creditReservations = new HashMap<>();

  public Money reservedCredit() {
    return creditReservations.values().stream().reduce(Money.ZERO, Money::add);
  }

  public void addReservation(String orderId, Money orderTotal) {
    creditReservations.put(orderId, orderTotal);
  }
}