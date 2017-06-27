package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderViewRepository extends MongoRepository<OrderView, String>, OrderViewRepositoryCustom {
}
