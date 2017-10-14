package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.Aggregates;
import io.eventuate.DefaultMissingApplyEventMethodStrategy;
import io.eventuate.Event;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.CustomerCreditLimitExceededEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.CustomerCreditReservedEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.eventuate.EventUtil.events;
import static org.junit.Assert.assertEquals;

public class CustomerTest {

  private Customer customer;
  private List<Event> events;

  @Before
  public void createEmptyCustomer() {
    customer = new Customer();
  }

  @Test
  public void testCreate() {

    process(new CreateCustomerCommand(CustomerMother.name, CustomerMother.creditLimit));

    assertEventEquals(new CustomerCreatedEvent(CustomerMother.name, CustomerMother.creditLimit));

    applyEventsToMutableAggregate();

    assertEquals(CustomerMother.creditLimit, customer.getCreditLimit());
    assertEquals(CustomerMother.creditLimit, customer.availableCredit());
  }

  private void applyEventsToMutableAggregate() {
    Aggregates.applyEventsToMutableAggregate(customer, events, DefaultMissingApplyEventMethodStrategy.INSTANCE);
  }


  @Test
  public void testReserveCredit() {
    initializeCustomer();

    process(new ReserveCreditCommand(CustomerMother.orderTotalWithinCreditLimit, CustomerMother.orderId));

    assertEventEquals(new CustomerCreditReservedEvent(CustomerMother.orderId, CustomerMother.orderTotalWithinCreditLimit));

    applyEventsToMutableAggregate();

    assertEquals(CustomerMother.creditLimit, customer.getCreditLimit());
    assertEquals(CustomerMother.creditLimit.subtract(CustomerMother.orderTotalWithinCreditLimit), customer.availableCredit());

  }


  @Test
  public void testCreditLimitExceeded() {
    initializeCustomer();

    process(new ReserveCreditCommand(CustomerMother.orderTotalThatExceedsCreditLimit, CustomerMother.orderId));

    assertEventEquals(new CustomerCreditLimitExceededEvent(CustomerMother.orderId));

    applyEventsToMutableAggregate();

    assertEquals(CustomerMother.creditLimit, customer.getCreditLimit());
    assertEquals(CustomerMother.creditLimit, customer.availableCredit());
  }

  private <T extends CustomerCommand> void process(T command) {
    events = customer.processCommand(command);
  }


  private void assertEventEquals(Event expectedEvent) {
    assertEquals(events(expectedEvent), events);
  }

  private void initializeCustomer() {
    process(new CreateCustomerCommand(CustomerMother.name, CustomerMother.creditLimit));
    applyEventsToMutableAggregate();
  }

}