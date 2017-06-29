package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEventHandlers
public class CustomerBackendConfiguration {

  @Bean
  public CustomerWorkflow customerWorkflow() {
    return new CustomerWorkflow();
  }


  @Bean
  public CustomerService customerService(AggregateRepository<Customer, CustomerCommand> customerRepository) {
    return new CustomerServiceImpl(customerRepository);
  }

  @Bean
  public AggregateRepository<Customer, CustomerCommand> customerRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(Customer.class, eventStore);
  }

}


