package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreditLimitExceededEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreditReservedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer extends ReflectiveMutableCommandProcessingAggregate<Customer, CustomerCommand> {

  private Money creditLimit;
  private Map<String, Money> creditReservations;

  public Money availableCredit() {
    return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
  }

  public Money getCreditLimit() {
    return creditLimit;
  }

  public List<Event> process(CreateCustomerCommand cmd) {
    return EventUtil.events(new CustomerCreatedEvent(cmd.getName(), cmd.getCreditLimit()));
  }

  public List<Event> process(ReserveCreditCommand cmd) {
    if (availableCredit().isGreaterThanOrEqual(cmd.getOrderTotal()))
      return EventUtil.events(new CustomerCreditReservedEvent(cmd.getOrderId(), cmd.getOrderTotal()));
    else
      return EventUtil.events(new CustomerCreditLimitExceededEvent(cmd.getOrderId()));
  }


  public void apply(CustomerCreatedEvent event) {
    this.creditLimit = event.getCreditLimit();
    this.creditReservations = new HashMap<>();
  }

  public void apply(CustomerCreditReservedEvent event) {
    this.creditReservations.put(event.getOrderId(), event.getOrderTotal());
  }

  public void apply(CustomerCreditLimitExceededEvent event) {
    // Do nothing
  }


}



