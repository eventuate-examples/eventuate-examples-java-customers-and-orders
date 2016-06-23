package net.chrisrichardson.eventstore.examples.customersandorders.common.customer.events;

import net.chrisrichardson.eventstore.Event;
import net.chrisrichardson.eventstore.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.customersandorders.customer.Customer")
public interface CustomerEvent extends Event {
}
