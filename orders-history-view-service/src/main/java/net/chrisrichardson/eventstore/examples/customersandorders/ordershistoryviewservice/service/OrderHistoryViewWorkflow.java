package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.service;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderApprovedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderRejectedEvent;
import org.springframework.beans.factory.annotation.Autowired;

@EventSubscriber(id = "orderHistoryWorkflow")
public class OrderHistoryViewWorkflow {

  private OrderHistoryViewService orderHistoryViewService;


  @Autowired
  public OrderHistoryViewWorkflow(OrderHistoryViewService orderHistoryViewService) {
    this.orderHistoryViewService = orderHistoryViewService;
  }

  @EventHandlerMethod
  public void createCustomer(DispatchedEvent<CustomerCreatedEvent> de) {
    String customerId = de.getEntityId();
    orderHistoryViewService.createCustomer(customerId, de.getEvent().getName(),
            de.getEvent().getCreditLimit());
  }

  @EventHandlerMethod
  public void createOrder(DispatchedEvent<OrderCreatedEvent> de) {
    String customerId = de.getEvent().getCustomerId();
    String orderId = de.getEntityId();
    Money orderTotal = de.getEvent().getOrderTotal();
    orderHistoryViewService.addOrder(customerId, orderId, orderTotal);
  }

  @EventHandlerMethod
  public void orderApproved(DispatchedEvent<OrderApprovedEvent> de) {
    String customerId = de.getEvent().getCustomerId();
    String orderId = de.getEntityId();
    orderHistoryViewService.approveOrder(customerId, orderId);  }

  @EventHandlerMethod
  public void orderRejected(DispatchedEvent<OrderRejectedEvent> de) {
    String customerId = de.getEvent().getCustomerId();
    String orderId = de.getEntityId();
    orderHistoryViewService.rejectOrder(customerId, orderId);
  }


}
