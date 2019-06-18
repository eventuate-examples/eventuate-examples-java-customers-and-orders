package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.web;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import io.eventuate.Aggregates;
import io.eventuate.DefaultMissingApplyEventMethodStrategy;
import io.eventuate.EntityIdAndVersion;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithMetadata;
import io.eventuate.common.id.Int128;
import net.chrisrichardson.eventstore.examples.customersandorders.common.customer.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend.CustomerService;
import org.junit.Before;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractCustomerServiceContractTest {

  private CustomerService customerService;

  @Before
  public void setup() {
    customerService = mock(CustomerService.class);
    RestAssuredMockMvc.standaloneSetup(new CustomerController(customerService));
    when(customerService.findById("1223232-none")).thenThrow(new EntityNotFoundException());

    Customer customer = Aggregates.recreateAggregate(Customer.class,
            Collections.singletonList(new CustomerCreatedEvent("Fred", new Money(4566))), DefaultMissingApplyEventMethodStrategy.INSTANCE);

    EntityWithMetadata<Customer> result =
            new EntityWithMetadata<>(new EntityIdAndVersion("1223232", new Int128(1, 2)), Optional.empty(),
            Collections.emptyList(), customer);
    when(customerService.findById("1223232"))
            .thenReturn(result);
  }
}
