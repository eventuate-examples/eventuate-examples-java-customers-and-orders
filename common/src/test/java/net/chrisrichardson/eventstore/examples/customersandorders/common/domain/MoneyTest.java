package net.chrisrichardson.eventstore.examples.customersandorders.common.domain;


import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoneyTest {

  private Money m1 = new Money(10);
  private Money m2 = new Money(15);

  @Test
  public void shouldReturnAmount() {
    assertEquals(new BigDecimal(10), new Money(10).getAmount());
  }

  @Test
  public void shouldCompare() {
    assertTrue(m2.isGreaterThanOrEqual(m2));
    assertTrue(m2.isGreaterThanOrEqual(m1));
  }

  @Test
  public void shouldAdd() {
    assertEquals(new Money(25), m1.add(m2));
  }

  @Test
  public void shouldSubtract() {
    assertEquals(new Money(5), m2.subtract(m1));
  }



}