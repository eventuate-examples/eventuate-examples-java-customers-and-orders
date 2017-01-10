package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EventHandlerContext;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderCreatedEvent;

import java.util.concurrent.CompletableFuture;

@EventSubscriber(id = "customerWorkflow")
public class CustomerWorkflow {

  @EventHandlerMethod
  public CompletableFuture<EntityWithIdAndVersion<Customer>> reserveCredit(
          EventHandlerContext<OrderCreatedEvent> ctx) {
    OrderCreatedEvent event = ctx.getEvent();
    Money orderTotal = event.getOrderTotal();
    String customerId = event.getCustomerId();
    String orderId = ctx.getEntityId();

    return ctx.update(Customer.class, customerId, new ReserveCreditCommand(orderTotal, orderId));
  }

}
