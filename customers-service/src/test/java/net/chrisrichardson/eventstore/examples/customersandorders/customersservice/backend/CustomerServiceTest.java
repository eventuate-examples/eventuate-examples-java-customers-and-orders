package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

  private CustomerService customerService;
  private AggregateRepository<Customer, CustomerCommand> aggregateRepository;

  @Before
  public void setUp() {
    aggregateRepository = mock(AggregateRepository.class);
    customerService = new CustomerServiceImpl(aggregateRepository);
  }

  @Test
  public void shouldCreateCustomer() {
    CompletableFuture<EntityWithIdAndVersion<Customer>> returned = new CompletableFuture<>();

    when(aggregateRepository.save(any(CreateCustomerCommand.class))).thenReturn(returned);

    CompletableFuture<EntityWithIdAndVersion<Customer>> result =
            customerService.createCustomer(CustomerMother.name, CustomerMother.creditLimit);

    assertSame(returned, result);

    ArgumentCaptor<CreateCustomerCommand> argument = ArgumentCaptor.forClass(CreateCustomerCommand.class);

    verify(aggregateRepository).save(argument.capture());
    verifyNoMoreInteractions(aggregateRepository);

    CreateCustomerCommand command = argument.getValue();

    assertEquals(CustomerMother.name, command.getName());
    assertEquals(CustomerMother.creditLimit, command.getCreditLimit());

  }

}