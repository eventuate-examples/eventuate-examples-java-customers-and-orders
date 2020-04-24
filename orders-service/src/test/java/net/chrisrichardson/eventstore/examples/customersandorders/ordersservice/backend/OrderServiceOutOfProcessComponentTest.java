package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.EntityWithMetadata;
import io.eventuate.sync.EventuateAggregateStore;
import net.chrisrichardson.eventstore.examples.customersandorders.orderscommmon.CreateOrderRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= OrderServiceOutOfProcessComponentTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
      properties = "customer.service.url=http://${DOCKER_HOST_IP:localhost}:8888/customers/{customerId}")
@AutoConfigureStubRunner(ids =
        {"net.chrisrichardson.eventstore.examples.customersandorders:common-contracts:+:stubs:8080"},
        workOffline = false)
@DirtiesContext
public class OrderServiceOutOfProcessComponentTest {

  @Value("${local.server.port}")
  private int port;

  @Value("${DOCKER_HOST_IP:localhost}")
  private String host;

  @Autowired
  private EventuateAggregateStore aggregateStore;

  private String baseUrl(String path) {
    return "http://"+ host +":" + port + path;
  }

  @Autowired
  private CustomerServiceProxy customerServiceProxy;

  @Before
  public void setup() {
    customerServiceProxy.setCustomerServiceUrl(String.format("http://%s:8080/customers/{customerId}", host));
  }

  @Test
  public void shouldCreateOrder() {
    String postUrl = baseUrl("/orders");

    String orderId = given().
      body(new CreateOrderRequest(TestData.customerId, TestData.orderTotal)).
            contentType("application/json").
    when().
           post(postUrl).
    then().
           statusCode(200).
    extract().
        path("orderId");

    assertNotNull(orderId);

    EntityWithMetadata<Order> orderWM = aggregateStore.find(Order.class, orderId);
    assertNotNull(orderWM.getEntity());
  }

  @Test
  public void shouldRejectCreateOrder() {
    String postUrl = baseUrl("/orders");

    given().
      body(new CreateOrderRequest(TestData.nonExistentCustomerId, TestData.orderTotal)).
            contentType("application/json").
    when().
           post(postUrl).
    then().
           statusCode(400);

  }
}
