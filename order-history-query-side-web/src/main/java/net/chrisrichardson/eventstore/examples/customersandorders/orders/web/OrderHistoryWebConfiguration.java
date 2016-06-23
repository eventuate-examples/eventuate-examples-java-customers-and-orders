package net.chrisrichardson.eventstore.examples.customersandorders.orders.web;

import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderHistoryViewConfiguration;
import net.chrisrichardson.eventstore.javaexamples.banking.web.util.ObservableReturnValueHandler;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@Import(OrderHistoryViewConfiguration.class)
public class OrderHistoryWebConfiguration {

  class FakeThing {}

  @Bean
  public FakeThing init(RequestMappingHandlerAdapter adapter) {
    // https://jira.spring.io/browse/SPR-13083
    List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(adapter.getReturnValueHandlers());
    handlers.add(0, new ObservableReturnValueHandler());
    adapter.setReturnValueHandlers(handlers);
    return new FakeThing();
  }

  @Bean
  public HttpMessageConverters customConverters() {
    HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
    return new HttpMessageConverters(additional);
  }

}
