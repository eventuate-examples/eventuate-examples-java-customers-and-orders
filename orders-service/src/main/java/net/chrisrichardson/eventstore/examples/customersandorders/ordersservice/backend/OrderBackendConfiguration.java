package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEventHandlers
public class OrderBackendConfiguration {

  @Bean
  public OrderWorkflow orderWorkflow() {
    return new OrderWorkflow();
  }


  @Bean
  public OrderService orderService(AggregateRepository<Order, OrderCommand> orderRepository) {
    return new OrderServiceImpl(orderRepository);
  }

  @Bean
  public AggregateRepository<Order, OrderCommand> orderRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(Order.class, eventStore);
  }

}


