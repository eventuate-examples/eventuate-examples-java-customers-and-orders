package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.domain.Customer;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.CustomerNotFoundException;
import net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.service.CustomerServiceProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceProxyTest {

  public static final String CUSTOMER_SERVICE_URL = "http://mycustomerservice/customers/{customerId}";
  private static final String CUSTOMER_ID = "customer_id";
  private RestTemplate restTemplate;
  private CustomerServiceProxy proxy;

  @BeforeEach
  public void setUp() {
    restTemplate = mock(RestTemplate.class);
    proxy = new CustomerServiceProxy(restTemplate);
    proxy.setCustomerServiceUrl(CUSTOMER_SERVICE_URL);
  }

  @Test
  public void shouldFindCustomer() {
    when(restTemplate.getForEntity(CUSTOMER_SERVICE_URL, Customer.class, CUSTOMER_ID))
            .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    proxy.verifyCustomerCustomerId(CUSTOMER_ID);
    verify(restTemplate).getForEntity(CUSTOMER_SERVICE_URL, Customer.class, CUSTOMER_ID);
  }

  @Test
  public void shouldNotFindCustomer() {
    when(restTemplate.getForEntity(CUSTOMER_SERVICE_URL, Customer.class, CUSTOMER_ID))
            .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    assertThrows(CustomerNotFoundException.class, () -> proxy.verifyCustomerCustomerId(CUSTOMER_ID));
  }
}
