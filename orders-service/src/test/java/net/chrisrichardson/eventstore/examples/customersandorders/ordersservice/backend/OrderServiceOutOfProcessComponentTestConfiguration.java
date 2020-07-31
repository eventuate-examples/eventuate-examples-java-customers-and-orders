package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import io.eventuate.local.java.spring.autoconfigure.EventuateDriverAutoConfiguration;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.web.OrderWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderWebConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class
})
@EnableAutoConfiguration(exclude = EventuateDriverAutoConfiguration.class)
public class OrderServiceOutOfProcessComponentTestConfiguration {
}
