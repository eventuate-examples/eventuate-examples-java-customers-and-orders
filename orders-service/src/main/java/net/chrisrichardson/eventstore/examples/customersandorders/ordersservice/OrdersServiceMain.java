package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice;

import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web.OrderWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderConfiguration.class, OrderWebConfiguration.class, })
@EnableAutoConfiguration
@ComponentScan
public class OrdersServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(OrdersServiceMain.class, args);
  }
}
