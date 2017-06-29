package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

public class TestData {
  static String customerId = "1223232";
  static String nonExistentCustomerId = "1223232-none";

  static Money orderTotal = new Money((int) (System.currentTimeMillis() % 1204));
}
