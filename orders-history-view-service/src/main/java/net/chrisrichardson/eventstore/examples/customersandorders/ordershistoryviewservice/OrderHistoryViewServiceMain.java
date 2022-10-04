package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.web.OrderHistoryViewWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderHistoryViewConfiguration.class, OrderHistoryViewWebConfiguration.class, })
@EnableAutoConfiguration
@ComponentScan
public class OrderHistoryViewServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(OrderHistoryViewServiceMain.class, args);
  }
}
