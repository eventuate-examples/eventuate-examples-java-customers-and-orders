package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.web;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend.OrderHistoryViewBackendConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@ComponentScan
@Import(OrderHistoryViewBackendConfiguration.class)
public class OrderHistoryViewWebConfiguration {

  @Bean
  public HttpMessageConverters customConverters() {
    HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
    return new HttpMessageConverters(additional);
  }

}
