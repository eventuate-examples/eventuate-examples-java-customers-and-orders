package net.chrisrichardson.eventstore.examples.customersandorders.orders.web.controllers;

import net.chrisrichardson.eventstore.examples.customersandorders.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CompletableFuture<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal())
                .thenApply(ewidv -> new CreateOrderResponse(ewidv.getEntityId()));
    }
}
