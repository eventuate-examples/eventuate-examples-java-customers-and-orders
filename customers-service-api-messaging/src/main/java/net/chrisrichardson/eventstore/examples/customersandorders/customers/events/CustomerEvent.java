package net.chrisrichardson.eventstore.examples.customersandorders.customers.events;


import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.Customer")
public interface CustomerEvent extends Event {
}
