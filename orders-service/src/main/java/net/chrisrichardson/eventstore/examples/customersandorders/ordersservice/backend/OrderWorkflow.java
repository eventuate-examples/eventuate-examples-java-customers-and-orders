package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;


import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EventHandlerContext;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.CustomerCreditLimitExceededEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.CustomerCreditReservedEvent;

import java.util.concurrent.CompletableFuture;

@EventSubscriber(id="orderWorkflow")
public class OrderWorkflow {

  @EventHandlerMethod
  public CompletableFuture<EntityWithIdAndVersion<Order>>
        creditLimitReserved(EventHandlerContext<CustomerCreditReservedEvent> ctx) {
    String orderId = ctx.getEvent().getOrderId();

    return ctx.update(Order.class, orderId, new ApproveOrderCommand());
  }

  @EventHandlerMethod
  public CompletableFuture<EntityWithIdAndVersion<Order>>
        creditLimitExceeded(EventHandlerContext<CustomerCreditLimitExceededEvent> ctx) {
    String orderId = ctx.getEvent().getOrderId();

    return ctx.update(Order.class, orderId, new RejectOrderCommand());
  }

}
