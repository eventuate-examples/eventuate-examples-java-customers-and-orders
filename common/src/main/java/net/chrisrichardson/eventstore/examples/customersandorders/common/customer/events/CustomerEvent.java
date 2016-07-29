package net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events;


import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.customer.Customer")
public interface CustomerEvent extends Event {
}
