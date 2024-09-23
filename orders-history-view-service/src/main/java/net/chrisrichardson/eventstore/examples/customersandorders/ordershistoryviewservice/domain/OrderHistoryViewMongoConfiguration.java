package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.service.OrderHistoryViewService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class OrderHistoryViewMongoConfiguration {
  @Bean
  public OrderHistoryViewService orderHistoryViewService(CustomerViewRepository customerViewRepository, OrderViewRepository orderViewRepository) {
    return new OrderHistoryViewService(customerViewRepository, orderViewRepository);
  }
}
