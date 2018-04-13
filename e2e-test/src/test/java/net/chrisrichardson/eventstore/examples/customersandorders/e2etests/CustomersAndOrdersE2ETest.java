package net.chrisrichardson.eventstore.examples.customersandorders.e2etests;

import io.eventuate.CompletableFutureUtil;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.commontest.AbstractCustomerAndOrdersIntegrationTest;
import net.chrisrichardson.eventstore.examples.customersandorders.customerscommon.CreateCustomerRequest;
import net.chrisrichardson.eventstore.examples.customersandorders.customerscommon.CreateCustomerResponse;
import net.chrisrichardson.eventstore.examples.customersandorders.orderscommmon.CreateOrderRequest;
import net.chrisrichardson.eventstore.examples.customersandorders.orderscommmon.CreateOrderResponse;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderView;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomersAndOrdersE2ETestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CustomersAndOrdersE2ETest extends AbstractCustomerAndOrdersIntegrationTest {

  @Value("#{systemEnvironment['DOCKER_HOST_IP']}")
  private String hostName;

  private String baseUrlCustomers(String path) {
    return "http://"+hostName+":8081/" + path;
  }

  private String baseUrlOrders(String path) {
    return "http://"+hostName+":8083/" + path;
  }

  private String baseUrlOrderHistory(String path) {
    return "http://"+hostName+":8082/" + path;
  }


  @Autowired
  RestTemplate restTemplate;

  private CustomerView getCustomer(String customerId) {
    try {
      ResponseEntity<CustomerView> getCustomer =
              restTemplate.getForEntity(baseUrlOrderHistory("customers/" + customerId), CustomerView.class);
      assertEquals(HttpStatus.OK, getCustomer.getStatusCode());
      return getCustomer.getBody();
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND)
        throw new RuntimeException("Cannot find customer "+ customerId, e);
      else
        throw e;
    }
  }


  @Override
  protected CustomerView getCustomerView(String customerId) {
    return getCustomer(customerId);
  }

  @Override
  protected OrderView getOrderView(String orderId) {
    try {
      ResponseEntity<OrderView> getCustomer =
              restTemplate.getForEntity(baseUrlOrderHistory("orders/" + orderId), OrderView.class);
      assertEquals(HttpStatus.OK, getCustomer.getStatusCode());
      return getCustomer.getBody();
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND)
        throw new RuntimeException("Cannot find order "+ orderId, e);
      else
        throw e;
    }
  }

  @Override
  protected String createOrder(String customerId, Money orderTotal) {
    try {
      ResponseEntity<CreateOrderResponse> orderResponse =
              restTemplate.postForEntity(baseUrlOrders("orders"), new CreateOrderRequest(customerId, orderTotal), CreateOrderResponse.class);
      assertEquals(HttpStatus.OK, orderResponse.getStatusCode());
      return orderResponse.getBody().getOrderId();
    } catch (HttpClientErrorException e) {
      switch (e.getStatusCode()) {
        case BAD_REQUEST:
          throw new IntegrationTestCustomerNotFoundException(e);
        default:
          throw e;
      }
    }
  }

  @Override
  protected String createCustomer(Money creditLimit) {
    ResponseEntity<CreateCustomerResponse> customerResponse =
            restTemplate.postForEntity(baseUrlCustomers("customers"), new CreateCustomerRequest("Fred", creditLimit), CreateCustomerResponse.class);

    assertEquals(HttpStatus.OK, customerResponse.getStatusCode());
    CreateCustomerResponse r = customerResponse.getBody();
    return r.getCustomerId();
  }
}
