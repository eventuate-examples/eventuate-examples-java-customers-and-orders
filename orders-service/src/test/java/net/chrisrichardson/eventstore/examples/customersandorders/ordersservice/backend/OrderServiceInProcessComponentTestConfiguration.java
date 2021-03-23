package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import io.eventuate.local.java.spring.autoconfigure.EventuateDriverAutoConfiguration;
import io.eventuate.util.spring.swagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.OrderConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web.OrderWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@Configuration
@Import({OrderConfiguration.class,
        OrderWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
        })
@EnableAutoConfiguration(exclude = {EventuateDriverAutoConfiguration.class, CommonSwaggerConfiguration.class})
public class OrderServiceInProcessComponentTestConfiguration {

  @Bean
  @Primary
  public RestTemplate restTemplate() {
    return mock(RestTemplate.class);
  }

}
