package net.chrisrichardson.eventstore.examples.customersandorders.orders.events;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.domain.Order")
public interface OrderEvent extends Event {
}
