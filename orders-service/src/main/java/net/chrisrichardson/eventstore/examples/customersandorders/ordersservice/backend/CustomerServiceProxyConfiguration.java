package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerServiceProxyConfiguration {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public CustomerService customerServiceProxy(RestTemplate restTemplate) {
    return new CustomerServiceProxy(restTemplate);
  }
}
