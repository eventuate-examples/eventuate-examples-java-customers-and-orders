package net.chrisrichardson.eventstore.examples.customersandorders.webtests;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.commontest.AbstractCustomerAndOrdersIntegrationTest;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.web.controllers.CreateCustomerRequest;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.web.controllers.CreateCustomerResponse;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.web.controllers.CreateOrderRequest;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.web.controllers.CreateOrderResponse;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderView;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomersAndOrdersWebTestConfiguration.class)
@WebIntegrationTest
public class CustomersAndOrdersWebTest  extends AbstractCustomerAndOrdersIntegrationTest {

  @Value("${local.server.port}")
  private int port;

  private String baseUrl(String path) {
    return "http://localhost:" + port + "/" + path;
  }

  @Autowired
  RestTemplate restTemplate;

  private Observable<CustomerView> getCustomer(String customerId) {
    try {
      ResponseEntity<CustomerView> getCustomer =
              restTemplate.getForEntity(baseUrl("customers/" + customerId), CustomerView.class);
      assertEquals(HttpStatus.OK, getCustomer.getStatusCode());
      return Observable.just(getCustomer.getBody());
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND)
        return Observable.empty();
      else
        throw e;
    }
  }


  @Override
  protected CustomerView getCustomerView(String customerId) {
    return await(getCustomer(customerId));
  }

  @Override
  protected OrderView getOrderView(String orderId) {
    Observable<OrderView> result;
    try {
      ResponseEntity<OrderView> getCustomer =
              restTemplate.getForEntity(baseUrl("orders/" + orderId), OrderView.class);
      assertEquals(HttpStatus.OK, getCustomer.getStatusCode());
      result = Observable.just(getCustomer.getBody());
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND)
        result = Observable.empty();
      else
        throw e;
    }
    return await(result);
  }

  @Override
  protected String createOrder(String customerId, Money orderTotal) {
    ResponseEntity<CreateOrderResponse> orderResponse =
            restTemplate.postForEntity(baseUrl("orders"), new CreateOrderRequest(customerId, orderTotal), CreateOrderResponse.class);
    assertEquals(HttpStatus.OK, orderResponse.getStatusCode());
    return orderResponse.getBody().getOrderId();
  }

  @Override
  protected String createCustomer(Money creditLimit) {
    ResponseEntity<CreateCustomerResponse> customerResponse =
            restTemplate.postForEntity(baseUrl("customers"), new CreateCustomerRequest("Fred", creditLimit), CreateCustomerResponse.class);

    assertEquals(HttpStatus.OK, customerResponse.getStatusCode());
    CreateCustomerResponse r = customerResponse.getBody();
    return r.getCustomerId();
  }
}
