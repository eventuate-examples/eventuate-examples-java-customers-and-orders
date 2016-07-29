package net.chrisrichardson.eventstore.examples.customersandorders.integrationtest;

import io.eventuate.javaclient.spring.jdbc.EventuateJdbcEventStoreConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.customer.CustomerConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.order.OrderConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderHistoryViewConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CustomerConfiguration.class, OrderConfiguration.class, OrderHistoryViewConfiguration.class, EventuateJdbcEventStoreConfiguration.class})
@EnableAutoConfiguration
public class CustomerAndOrdersIntegrationTestConfiguration {
}
