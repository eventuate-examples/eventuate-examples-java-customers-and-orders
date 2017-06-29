package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web.OrderWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@Configuration
@Import({OrderWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
        })
@EnableAutoConfiguration
public class OrderServiceInProcessComponentTestConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    return mock(RestTemplate.class);
  }

}
