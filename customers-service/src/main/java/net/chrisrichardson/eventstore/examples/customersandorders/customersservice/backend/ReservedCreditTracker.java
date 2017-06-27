package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.HashMap;
import java.util.Map;

public class ReservedCreditTracker {
  private Map<String, Money> creditReservations = new HashMap<>();

  Money reservedCredit() {
    return creditReservations.values().stream().reduce(Money.ZERO, Money::add);
  }

  void addReservation(String orderId, Money orderTotal) {
    creditReservations.put(orderId, orderTotal);
  }
}