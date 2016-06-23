package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

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
