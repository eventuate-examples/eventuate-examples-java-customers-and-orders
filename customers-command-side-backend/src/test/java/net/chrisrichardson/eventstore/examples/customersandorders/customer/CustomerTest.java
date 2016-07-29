package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreditLimitedExceededEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreditReservedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

  Money creditLimit = new Money(56);
  String name = "Fred";
  String orderId = "myorder";

  @Test
  public void testCreate() {
    Customer customer = new Customer();

    List<Event> events = customer.process(new CreateCustomerCommand(name, creditLimit));

    assertEquals(EventUtil.events(new CustomerCreatedEvent(name, creditLimit)), events);

    customer.apply((CustomerCreatedEvent) events.get(0));

    assertEquals(creditLimit, customer.getCreditLimit());
    assertEquals(creditLimit, customer.availableCredit());
  }

  @Test
  public void testReserveCredit() {
    Customer customer = createCustomer();

    Money orderTotal = new Money(5);

    List<Event> events = customer.process(new ReserveCreditCommand(orderTotal, orderId));

    assertEquals(EventUtil.events(new CustomerCreditReservedEvent(orderId, orderTotal)), events);

    customer.apply((CustomerCreditReservedEvent) events.get(0));

    assertEquals(creditLimit, customer.getCreditLimit());
    assertEquals(creditLimit.subtract(orderTotal), customer.availableCredit());


  }

  private Customer createCustomer() {
    Customer customer = new Customer();

    customer.apply((CustomerCreatedEvent)customer.process(new CreateCustomerCommand(name, creditLimit)).get(0));
    return customer;
  }

  @Test
  public void testCreditLimitExceeded() {
    Customer customer = createCustomer();

    Money orderTotal = creditLimit.add(new Money(1));

    List<Event> events = customer.process(new ReserveCreditCommand(orderTotal, orderId));

    assertEquals(EventUtil.events(new CustomerCreditLimitedExceededEvent(orderId)), events);

    customer.apply((CustomerCreditLimitedExceededEvent) events.get(0));

    assertEquals(creditLimit, customer.getCreditLimit());
    assertEquals(creditLimit, customer.availableCredit());


  }


}