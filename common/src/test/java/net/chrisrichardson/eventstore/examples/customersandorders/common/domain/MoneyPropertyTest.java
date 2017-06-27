package net.chrisrichardson.eventstore.examples.customersandorders.common.domain;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(JUnitQuickcheck.class)
public class MoneyPropertyTest {

  @Property
  public void add(int x, @InRange(minInt = 0, maxInt = Integer.MAX_VALUE / 2) int y) {

    assumeThat(x, greaterThanOrEqualTo(0));
    assumeThat(x, lessThanOrEqualTo(Integer.MAX_VALUE / 2));

    assertEquals(new Money(x).add(new Money(y)), new Money(x + y));
  }

  @Property
  public void isGreaterThanOrEqual(int x, int y) {
    assertEquals(new Money(x).isGreaterThanOrEqual(new Money(y)), x >=  y);
  }

  @Property
  public void equals(int x, int y) {
    assertEquals(new Money(x).equals(new Money(y)), x ==  y);
  }


}
