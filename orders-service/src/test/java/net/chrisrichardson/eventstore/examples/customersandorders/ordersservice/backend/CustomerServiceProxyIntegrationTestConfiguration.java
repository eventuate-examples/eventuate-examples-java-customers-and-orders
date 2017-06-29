package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CustomerServiceProxyConfiguration.class)
@EnableAutoConfiguration
public class CustomerServiceProxyIntegrationTestConfiguration {
}
