package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web;

import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.orderscommmon.CreateOrderRequest;
import net.chrisrichardson.eventstore.examples.customersandorders.orderscommmon.CreateOrderResponse;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend.CustomerNotFoundException;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend.Order;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        try {
            EntityWithIdAndVersion<Order> order =
                    orderService.createOrder(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal());
            return new ResponseEntity<>(new CreateOrderResponse(order.getEntityId()), HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
