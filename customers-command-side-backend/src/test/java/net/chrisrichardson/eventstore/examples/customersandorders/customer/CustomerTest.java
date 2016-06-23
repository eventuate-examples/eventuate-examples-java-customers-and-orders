package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import net.chrisrichardson.eventstore.CommandProcessingAggregates;
import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.Event;
import net.chrisrichardson.eventstore.EventUtil;
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
  EntityIdentifier orderId = new EntityIdentifier("myorder");

  @Test
  public void testCreate() {
    Customer customer = new Customer();

    List<Event> events = CommandProcessingAggregates.processToList(customer, new CreateCustomerCommand(name, creditLimit));

    assertEquals(EventUtil.events(new CustomerCreatedEvent(name, creditLimit)), events);

    CommandProcessingAggregates.applyEventsToMutableAggregate(customer, events);

    assertEquals(creditLimit, customer.getCreditLimit());
    assertEquals(creditLimit, customer.availableCredit());


  }

  @Test
  public void testReserveCredit() {
    Customer customer = createCustomer();

    Money orderTotal = new Money(5);

    List<Event> events = CommandProcessingAggregates.processToList(customer, new ReserveCreditCommand(orderTotal, orderId));

    assertEquals(EventUtil.events(new CustomerCreditReservedEvent(orderId, orderTotal)), events);

    CommandProcessingAggregates.applyEventsToMutableAggregate(customer, events);

    assertEquals(creditLimit, customer.getCreditLimit());
    assertEquals(creditLimit.subtract(orderTotal), customer.availableCredit());


  }

  private Customer createCustomer() {
    Customer customer = new Customer();

    CommandProcessingAggregates.applyEventsToMutableAggregate(customer,
            CommandProcessingAggregates.processToList(customer, new CreateCustomerCommand(name, creditLimit)));
    return customer;
  }

  @Test
  public void testCreditLimitExceeded() {
    Customer customer = createCustomer();

    Money orderTotal = creditLimit.add(new Money(1));

    List<Event> events = CommandProcessingAggregates.processToList(customer, new ReserveCreditCommand(orderTotal, orderId));

    assertEquals(EventUtil.events(new CustomerCreditLimitedExceededEvent(orderId)), events);

    CommandProcessingAggregates.applyEventsToMutableAggregate(customer, events);

    assertEquals(creditLimit, customer.getCreditLimit());
    assertEquals(creditLimit, customer.availableCredit());


  }


}