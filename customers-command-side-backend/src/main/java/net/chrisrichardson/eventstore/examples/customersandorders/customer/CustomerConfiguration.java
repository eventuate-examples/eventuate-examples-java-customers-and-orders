package net.chrisrichardson.eventstore.examples.customersandorders.customer;

import net.chrisrichardson.eventstore.EventStore;
import net.chrisrichardson.eventstore.javaapi.consumer.EnableJavaEventHandlers;
import net.chrisrichardson.eventstore.repository.AggregateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJavaEventHandlers
public class CustomerConfiguration {

  @Bean
  public CustomerWorkflow customerWorkflow() {
    return new CustomerWorkflow();
  }


  @Bean
  public CustomerService customerService(AggregateRepository<Customer, CustomerCommand> customerRepository) {
    return new CustomerServiceImpl(customerRepository);
  }

  @Bean
  public AggregateRepository<Customer, CustomerCommand> customerRepository(EventStore eventStore) {
    return new AggregateRepository<>(Customer.class, eventStore);
  }

}


