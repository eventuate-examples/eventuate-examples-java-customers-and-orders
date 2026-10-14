package net.chrisrichardson.eventstore.examples.customersandorders.e2etests;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
public class CustomersAndOrdersE2ETestConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
