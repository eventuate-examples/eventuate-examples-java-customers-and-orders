package net.chrisrichardson.eventstore.examples.customersandorders.common.order;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend.Order")
public interface OrderEvent extends Event {
}
