package net.chrisrichardson.eventstore.examples.customersandorders.order;

import net.chrisrichardson.eventstore.EventStore;
import net.chrisrichardson.eventstore.javaapi.consumer.EnableJavaEventHandlers;
import net.chrisrichardson.eventstore.repository.AggregateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJavaEventHandlers
public class OrderConfiguration {

  @Bean
  public OrderWorkflow orderWorkflow() {
    return new OrderWorkflow();
  }


  @Bean
  public OrderService orderService(AggregateRepository<Order, OrderCommand> orderRepository) {
    return new OrderServiceImpl(orderRepository);
  }

  @Bean
  public AggregateRepository<Order, OrderCommand> orderRepository(EventStore eventStore) {
    return new AggregateRepository<>(Order.class, eventStore);
  }

}


