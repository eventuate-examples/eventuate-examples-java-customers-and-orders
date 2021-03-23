package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.util.spring.swagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.CustomerServiceProxyConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CustomerServiceProxyConfiguration.class)
@EnableAutoConfiguration(exclude = CommonSwaggerConfiguration.class)
public class CustomerServiceProxyIntegrationTestConfiguration {
}
