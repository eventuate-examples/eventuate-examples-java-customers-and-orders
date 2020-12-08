package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.domain.Order;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.CustomerService;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.CustomerServiceProxyConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.OrderCommand;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.OrderService;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.OrderServiceImpl;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.OrderWorkflow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CustomerServiceProxyConfiguration.class)
public class OrderConfiguration {

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


