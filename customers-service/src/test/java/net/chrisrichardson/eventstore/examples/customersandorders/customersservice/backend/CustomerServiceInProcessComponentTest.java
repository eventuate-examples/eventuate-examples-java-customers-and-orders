package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.webapi.CreateCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes= CustomerServiceInProcessComponentTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceInProcessComponentTest {

  @Value("${local.server.port}")
  private int port;

  @Value("${DOCKER_HOST_IP:localhost}")
  private String host;

  private String baseUrl(String path) {
    return "http://" + host + ":" + port + path;
  }

  @Test
  public void shouldCreateOrder() {
    String postUrl = baseUrl("/customers");

    String customerId = given().
      body(new CreateCustomerRequest("John Doe", new Money(1234))).
            contentType("application/json").
    when().
           post(postUrl).
    then().
           statusCode(200).
    extract().
        path("customerId");

    assertNotNull(customerId);


    Integer creditLimit = given().
            when().
            get(postUrl + "/" + customerId).
            then().
            statusCode(200).
            extract().
            path("creditLimit.amount");

    assertEquals(new Integer(1234), creditLimit);

  }
}
