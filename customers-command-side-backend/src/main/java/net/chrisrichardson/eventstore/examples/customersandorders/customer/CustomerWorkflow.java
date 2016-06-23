package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderCreatedEvent;
import net.chrisrichardson.eventstore.javaapi.consumer.EventHandlerContext;
import net.chrisrichardson.eventstore.subscriptions.EventHandlerMethod;
import net.chrisrichardson.eventstore.subscriptions.EventSubscriber;
import rx.Observable;

@EventSubscriber(id="customerWorkflow")
public class CustomerWorkflow {

  @EventHandlerMethod
  public Observable<?> reserveCredit(EventHandlerContext<OrderCreatedEvent> ctx) {
    OrderCreatedEvent event = ctx.getEvent();
    Money orderTotal = event.getOrderTotal();
    EntityIdentifier customerId = event.getCustomerId();
    EntityIdentifier orderId = ctx.getEntityIdentifier();

    return ctx.update(Customer.class, customerId, new ReserveCreditCommand(orderTotal, orderId));
  }

}
