package net.chrisrichardson.eventstore.examples.customersandorders.orders.web.controllers;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.order.Order;
import net.chrisrichardson.eventstore.examples.customersandorders.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

@RestController
public class OrderController {

  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @RequestMapping(value="/orders", method= RequestMethod.POST)
  public Observable<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {

    Observable<EntityWithIdAndVersion<Order>> order =
            orderService.createOrder(new EntityIdentifier(createOrderRequest.getCustomerId()),
                    createOrderRequest.getOrderTotal());

    return order.map(ewidv -> new CreateOrderResponse(ewidv.getEntityIdentifier()));
  }



}
