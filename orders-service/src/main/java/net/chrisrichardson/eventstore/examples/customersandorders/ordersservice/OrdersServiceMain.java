package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice;

import io.eventuate.local.java.spring.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web.OrderWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderWebConfiguration.class, EventuateDriverConfiguration.class, CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
public class OrdersServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(OrdersServiceMain.class, args);
  }
}
