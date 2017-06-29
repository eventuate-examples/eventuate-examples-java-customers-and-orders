package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.CustomerView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerViewRepository
        extends MongoRepository<CustomerView, String>, CustomerViewRepositoryCustom {
}
