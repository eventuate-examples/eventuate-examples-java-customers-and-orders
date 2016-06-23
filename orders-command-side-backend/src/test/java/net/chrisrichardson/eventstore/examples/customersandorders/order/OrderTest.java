package net.chrisrichardson.eventstore.examples.customersandorders.order;

import net.chrisrichardson.eventstore.CommandProcessingAggregates;
import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.Event;
import net.chrisrichardson.eventstore.EventUtil;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderApprovedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderRejectedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderTest {

  Money orderTotal = new Money(10);
  EntityIdentifier customerId = new EntityIdentifier("customerID");

  @Test
  public void testCreate() {
    Order order = new Order();

    List<Event> events = CommandProcessingAggregates.processToList(order, new CreateOrderCommand(customerId, orderTotal));

    assertEquals(EventUtil.events(new OrderCreatedEvent(customerId, orderTotal)), events);

    CommandProcessingAggregates.applyEventsToMutableAggregate(order, events);

    assertEquals(OrderState.CREATED, order.getState());

  }

  @Test
  public void testApprove() {
    Order order = new Order();

    CommandProcessingAggregates.applyEventsToMutableAggregate(order,
            CommandProcessingAggregates.processToList(order, new CreateOrderCommand(customerId, orderTotal)));

    List<Event> events = CommandProcessingAggregates.processToList(order, new ApproveOrderCommand());

    assertEquals(EventUtil.events(new OrderApprovedEvent(customerId)), events);

    CommandProcessingAggregates.applyEventsToMutableAggregate(order, events);

    assertEquals(OrderState.APPROVED, order.getState());

  }

  @Test
  public void testReject() {
    Order order = new Order();

    CommandProcessingAggregates.applyEventsToMutableAggregate(order,
            CommandProcessingAggregates.processToList(order, new CreateOrderCommand(customerId, orderTotal)));

    List<Event> events = CommandProcessingAggregates.processToList(order, new RejectOrderCommand());

    assertEquals(EventUtil.events(new OrderRejectedEvent(customerId)), events);

    CommandProcessingAggregates.applyEventsToMutableAggregate(order, events);

    assertEquals(OrderState.REJECTED, order.getState());

  }
}