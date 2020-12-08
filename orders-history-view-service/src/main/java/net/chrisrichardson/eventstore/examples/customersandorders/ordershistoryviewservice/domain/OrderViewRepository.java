package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistory.webapi.OrderView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderViewRepository extends MongoRepository<OrderView, String>, OrderViewRepositoryCustom {
}
