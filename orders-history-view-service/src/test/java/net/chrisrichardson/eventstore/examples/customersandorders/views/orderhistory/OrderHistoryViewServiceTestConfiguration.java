package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend.OrderHistoryViewMongoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(OrderHistoryViewMongoConfiguration.class)
public class OrderHistoryViewServiceTestConfiguration {
}
