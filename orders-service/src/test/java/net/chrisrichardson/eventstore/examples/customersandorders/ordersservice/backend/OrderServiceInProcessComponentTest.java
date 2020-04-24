package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import io.eventuate.EntityWithMetadata;
import io.eventuate.sync.EventuateAggregateStore;
import net.chrisrichardson.eventstore.examples.customersandorders.orderscommmon.CreateOrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= OrderServiceInProcessComponentTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
      properties = "customer.service.url=http://${DOCKER_HOST_IP:localhost}:8888/customers/{customerId}")
public class OrderServiceInProcessComponentTest {

  @Value("${local.server.port}")
  private int port;

  @Value("${DOCKER_HOST_IP:localhost}")
  private String host;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private EventuateAggregateStore aggregateStore;

  private String baseUrl(String path) {
    return "http://" + host + ":" + port + path;
  }

  @Test
  public void shouldCreateOrder() {
    String postUrl = baseUrl("/orders");

    when(restTemplate.getForEntity(String.format("http://%s:8888/customers/{customerId}", host),
            Customer.class, TestData.customerId))
            .thenReturn(new ResponseEntity<>(HttpStatus.OK));

    String orderId = given().
      body(new CreateOrderRequest(TestData.customerId, TestData.orderTotal)).
            contentType("application/json").
    when().
           post(postUrl).
    then().
           statusCode(200).
    extract().
        path("orderId");

    verify(restTemplate).getForEntity(String.format("http://%s:8888/customers/{customerId}", host),
            Customer.class, TestData.customerId);

    assertNotNull(orderId);

    EntityWithMetadata<Order> orderWM = aggregateStore.find(Order.class, orderId);
    assertNotNull(orderWM.getEntity());
  }
}
