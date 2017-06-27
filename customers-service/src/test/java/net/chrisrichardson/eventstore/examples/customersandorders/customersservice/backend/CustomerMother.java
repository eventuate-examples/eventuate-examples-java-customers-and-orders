package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

public class CustomerMother {

  static Money creditLimit = new Money(56);
  static Money orderTotalThatExceedsCreditLimit = creditLimit.add(new Money(1));
  static String name = "Fred";
  static String orderId = "myorder";
  static Money orderTotalWithinCreditLimit = new Money(5);
}
