package net.chrisrichardson.eventstore.examples.customersandorders.integrationtest;

import net.chrisrichardson.eventstore.examples.customersandorders.customer.CustomerConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.order.OrderConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderHistoryViewConfiguration;
import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CustomerConfiguration.class, OrderConfiguration.class, OrderHistoryViewConfiguration.class, JdbcEventStoreConfiguration.class})
public class CustomerAndOrdersIntegrationTestConfiguration {
}
