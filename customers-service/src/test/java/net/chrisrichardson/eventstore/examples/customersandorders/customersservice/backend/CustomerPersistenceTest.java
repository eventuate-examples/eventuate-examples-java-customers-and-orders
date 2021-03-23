package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.sync.AggregateRepository;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.CreateCustomerCommand;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.CustomerCommand;
import net.chrisrichardson.eventstore.examples.customersandorders.customersservice.domain.ReserveCreditCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= CustomerServiceInProcessComponentTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.NONE)
public class CustomerPersistenceTest {

  @Autowired
  private AggregateRepository<Customer, CustomerCommand> aggregateRepository;

  @Test
  public void shouldCreateAndUpdateCustomer() {
    EntityWithIdAndVersion<Customer> cwm = aggregateRepository.save(new CreateCustomerCommand("Fred", new Money(1234)));

    aggregateRepository.update(cwm.getEntityId(), new ReserveCreditCommand(new Money(11), "order-1"));
    aggregateRepository.update(cwm.getEntityId(), new ReserveCreditCommand(new Money(11), "order-2"));
    aggregateRepository.update(cwm.getEntityId(), new ReserveCreditCommand(new Money(11), "order-3"));

  }
}
