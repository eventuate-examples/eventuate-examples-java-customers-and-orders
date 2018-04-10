package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderApprovedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderRejectedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;

import java.util.List;

import static io.eventuate.EventUtil.events;

public class Order
        extends ReflectiveMutableCommandProcessingAggregate<Order, OrderCommand> {

  private OrderState state;
  private String customerId;

  public List<Event> process(CreateOrderCommand cmd) {
    return events(new OrderCreatedEvent(cmd.getCustomerId(), cmd.getOrderTotal()));
  }

  public void apply(OrderCreatedEvent event) {
    this.state = OrderState.CREATED;
    this.customerId = event.getCustomerId();
  }

  public OrderState getState() {
    return state;
  }

  public List<Event> process(ApproveOrderCommand cmd) {
    return events(new OrderApprovedEvent(customerId));
  }

  public List<Event> process(RejectOrderCommand cmd) {
    return events(new OrderRejectedEvent(customerId));
  }


  public void apply(OrderApprovedEvent event) {
    this.state = OrderState.APPROVED;
  }


  public void apply(OrderRejectedEvent event) {
    this.state = OrderState.REJECTED;
  }


}
