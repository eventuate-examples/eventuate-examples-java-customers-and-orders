package net.chrisrichardson.eventstore.examples.customersandorders.webtests;

import io.eventuate.javaclient.spring.jdbc.EventuateJdbcEventStoreConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.web.CustomerWebConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.web.OrderHistoryWebConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.web.OrderWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@Import({CustomerWebConfiguration.class, OrderWebConfiguration.class, OrderHistoryWebConfiguration.class,
          EventuateJdbcEventStoreConfiguration.class})
@EnableAutoConfiguration
public class CustomersAndOrdersWebTestConfiguration {

  @Bean
  public RestTemplate restTemplate(HttpMessageConverters converters) {
    RestTemplate restTemplate = new RestTemplate();
    HttpMessageConverter<?> httpMessageConverter = converters.getConverters().get(0);
    List<? extends HttpMessageConverter<?>> httpMessageConverters = Arrays.asList(new MappingJackson2HttpMessageConverter());
    restTemplate.setMessageConverters((List<HttpMessageConverter<?>>) httpMessageConverters);
    return restTemplate;
  }

}
