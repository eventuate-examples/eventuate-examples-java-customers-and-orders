package net.chrisrichardson.eventstore.examples.customersandorders.common.customer;


import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend.Customer")
public interface CustomerEvent extends Event {
}
