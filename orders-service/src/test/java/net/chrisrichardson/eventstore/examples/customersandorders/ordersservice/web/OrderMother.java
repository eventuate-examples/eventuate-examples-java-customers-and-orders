package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web;

import io.eventuate.EntityIdAndVersion;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.common.id.Int128;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend.Order;
import org.springframework.http.MediaType;

public class OrderMother {
  static MediaType applicationJson = MediaType.parseMediaType("application/json;charset=UTF-8");
  static String customerId = "customer_id";
  static Money orderTotal = new Money(1234);
  static String orderId = "entity-id";
  static EntityWithIdAndVersion<Order> createOrderResult =
          new EntityWithIdAndVersion<>(
                  new EntityIdAndVersion(orderId, new Int128(1, 3)),
                  new Order());
}
