package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import io.eventuate.local.java.spring.autoconfigure.EventuateDriverAutoConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.web.CustomerWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CustomerWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
        })
@EnableAutoConfiguration(exclude = EventuateDriverAutoConfiguration.class)
public class CustomerServiceInProcessComponentTestConfiguration {


}
