package net.chrisrichardson.eventstore.examples.customersandorders.customersservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.customerscommon.CreateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
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
