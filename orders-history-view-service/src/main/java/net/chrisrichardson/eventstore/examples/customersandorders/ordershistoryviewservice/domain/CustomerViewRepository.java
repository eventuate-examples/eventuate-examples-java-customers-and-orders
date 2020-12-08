package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistory.webapi.CustomerView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerViewRepository
        extends MongoRepository<CustomerView, String>, CustomerViewRepositoryCustom {
}
