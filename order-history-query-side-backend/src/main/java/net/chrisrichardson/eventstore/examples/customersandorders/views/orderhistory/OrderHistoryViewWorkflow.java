package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderApprovedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderRejectedEvent;
import net.chrisrichardson.eventstore.subscriptions.DispatchedEvent;
import net.chrisrichardson.eventstore.subscriptions.EventHandler;
import net.chrisrichardson.eventstore.subscriptions.EventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

@EventSubscriber(id="orderHistoryWorkflow")
public class OrderHistoryViewWorkflow {

  private OrderHistoryViewService orderHistoryViewService;


  @Autowired
  public OrderHistoryViewWorkflow(OrderHistoryViewService orderHistoryViewService) {
    this.orderHistoryViewService = orderHistoryViewService;
  }

  @EventHandler
  public Observable<Object> createCustomer(DispatchedEvent<CustomerCreatedEvent> de) {
    String customerId = de.getEntityIdentifier().getId();
    orderHistoryViewService.createCustomer(customerId, de.event().getName(), de.event().getCreditLimit());
    return Observable.just(null);
  }

  @EventHandler
  public Observable<Object> createOrder(DispatchedEvent<OrderCreatedEvent> de) {
    String customerId = de.event().getCustomerId().getId();
    String orderId = de.entityId().id();
    Money orderTotal = de.event().getOrderTotal();
    orderHistoryViewService.addOrder(customerId, orderId, orderTotal);
    return Observable.just(null);
  }

  @EventHandler
  public Observable<Object> orderApproved(DispatchedEvent<OrderApprovedEvent> de) {
    EntityIdentifier customerId = de.event().getCustomerId();
    String orderId = de.entityId().id();
    orderHistoryViewService.approveOrder(customerId.getId(), orderId);
    return Observable.just(null);
  }

  @EventHandler
  public Observable<Object> orderRejected(DispatchedEvent<OrderRejectedEvent> de) {
    EntityIdentifier customerId = de.event().getCustomerId();
    String orderId = de.entityId().id();
    orderHistoryViewService.rejectOrder(customerId.getId(), orderId);
    return Observable.just(null);
  }

}
