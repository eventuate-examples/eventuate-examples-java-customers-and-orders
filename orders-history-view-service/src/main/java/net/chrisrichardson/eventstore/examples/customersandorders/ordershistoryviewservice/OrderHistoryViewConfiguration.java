package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain.OrderHistoryViewMongoConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.service.OrderHistoryViewService;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.service.OrderHistoryViewWorkflow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(OrderHistoryViewMongoConfiguration.class)
public class OrderHistoryViewConfiguration {

  @Bean
  public OrderHistoryViewWorkflow orderHistoryViewWorkflow(OrderHistoryViewService service) {
    return new OrderHistoryViewWorkflow(service);
  }
}
