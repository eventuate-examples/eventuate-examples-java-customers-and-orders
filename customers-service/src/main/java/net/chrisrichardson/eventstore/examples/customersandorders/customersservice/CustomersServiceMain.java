package net.chrisrichardson.eventstore.examples.customersandorders.customersservice;

import net.chrisrichardson.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.web.CustomerWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CustomerWebConfiguration.class, CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
@ComponentScan
public class CustomersServiceMain {
  public static void main(String[] args) {
    SpringApplication.run(CustomersServiceMain.class, args);
  }
}
