package net.chrisrichardson.eventstore.examples.customersandorders.integrationtest;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.commontest.AbstractCustomerAndOrdersIntegrationTest;
import net.chrisrichardson.eventstore.examples.customersandorders.customer.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.customer.CustomerService;
import net.chrisrichardson.eventstore.examples.customersandorders.order.Order;
import net.chrisrichardson.eventstore.examples.customersandorders.order.OrderService;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.CustomerViewRepository;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderView;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderViewRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerAndOrdersIntegrationTestConfiguration.class)
@IntegrationTest
public class CustomerAndOrdersIntegrationTest extends AbstractCustomerAndOrdersIntegrationTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerViewRepository customerViewRepository;

  @Autowired
  private OrderViewRepository orderViewRepository;

  @Override
  protected CustomerView getCustomerView(String customerId) {
    return customerViewRepository.findOne(customerId);
  }

  @Override
  protected OrderView getOrderView(String orderId) {
    return orderViewRepository.findOne(orderId);
  }

  @Override
  protected String createOrder(String customerId, Money orderTotal) {
    EntityWithIdAndVersion<Order> order = await(orderService.createOrder(new EntityIdentifier(customerId), orderTotal));

    String orderId = order.getEntityIdentifier().getId();

    logger.info("OrderId={}", orderId);
    logger.info("OrderId={}", order.entity().getState());
    return orderId;
  }

  @Override
  protected String createCustomer(Money creditLimit) {
    EntityWithIdAndVersion<Customer> customer =
            await(customerService.createCustomer("Fred", creditLimit));

    String customerId = customer.getEntityIdentifier().getId();
    logger.info("CustomerId={}", customerId);
    return customer.getEntityIdentifier().getId();
  }

}
