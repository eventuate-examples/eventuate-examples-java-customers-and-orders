package net.chrisrichardson.eventstore.examples.customersandorders.customersservice;

import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.CustomerCommand;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.service.CustomerService;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.service.CustomerServiceImpl;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.service.CustomerWorkflow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
  public AggregateRepository<Customer, CustomerCommand> customerRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<>(Customer.class, eventStore);
  }

}


