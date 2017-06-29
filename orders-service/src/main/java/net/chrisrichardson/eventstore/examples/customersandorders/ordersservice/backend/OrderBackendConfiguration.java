package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableEventHandlers
@Import(CustomerServiceProxyConfiguration.class)
public class OrderBackendConfiguration {

  @Bean
  public OrderWorkflow orderWorkflow() {
    return new OrderWorkflow();
  }

  @Bean
  public OrderService orderService(AggregateRepository<Order, OrderCommand> orderRepository,
                                   CustomerService customerServiceProxy) {
    return new OrderServiceImpl(orderRepository, customerServiceProxy);
  }

  @Bean
  public AggregateRepository<Order, OrderCommand> orderRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(Order.class, eventStore);
  }

}


