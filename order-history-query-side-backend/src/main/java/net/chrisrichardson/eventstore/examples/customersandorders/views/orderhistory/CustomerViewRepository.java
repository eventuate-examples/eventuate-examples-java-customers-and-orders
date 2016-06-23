package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerViewRepository extends MongoRepository<CustomerView, String> {
}
