package net.chrisrichardson.eventstore.examples.customersandorders.common.order;

import net.chrisrichardson.eventstore.Event;
import net.chrisrichardson.eventstore.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.order.Order")
public interface OrderEvent extends Event {
}
