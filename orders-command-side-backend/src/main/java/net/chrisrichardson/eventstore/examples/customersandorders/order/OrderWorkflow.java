package net.chrisrichardson.eventstore.examples.customersandorders.order;


import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreditLimitedExceededEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreditReservedEvent;
import net.chrisrichardson.eventstore.javaapi.consumer.EventHandlerContext;
import net.chrisrichardson.eventstore.subscriptions.EventHandlerMethod;
import net.chrisrichardson.eventstore.subscriptions.EventSubscriber;
import rx.Observable;

@EventSubscriber(id="orderWorkflow")
public class OrderWorkflow {

  @EventHandlerMethod
  public Observable<?> creditLimitReserved(EventHandlerContext<CustomerCreditReservedEvent> ctx) {
    EntityIdentifier orderId = ctx.getEvent().getOrderId();

    return ctx.update(Order.class, orderId, new ApproveOrderCommand());
  }

  @EventHandlerMethod
  public Observable<?> creditLimitExceeded(EventHandlerContext<CustomerCreditLimitedExceededEvent> ctx) {
    EntityIdentifier orderId = ctx.getEvent().getOrderId();

    return ctx.update(Order.class, orderId, new RejectOrderCommand());
  }

}
