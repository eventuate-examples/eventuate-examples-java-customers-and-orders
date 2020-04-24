package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CustomerServiceProxyIntegrationTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.NONE,
        properties={"customer.service.url=http://${DOCKER_HOST_IP:localhost}:8080"}
)
@AutoConfigureStubRunner(ids =
        {"net.chrisrichardson.eventstore.examples.customersandorders:common-contracts:+:stubs:8080"},
//        repositoryRoot = "file:///Users/cer/.m2/repository",
        workOffline = false)
@DirtiesContext
public class CustomerServiceProxyWireMockBasedIntegrationTest {

  @Autowired
  private CustomerServiceProxy customerServiceProxy;

  @Value("${DOCKER_HOST_IP:localhost}")
  private String host;

  @Test
  public void shouldVerifyExistingCustomer() {
    customerServiceProxy.setCustomerServiceUrl(String.format("http://%s:8080/customers/{customerId}", host));
    customerServiceProxy.verifyCustomerCustomerId(TestData.customerId);
  }

  @Test(expected = CustomerNotFoundException.class)
  public void shouldRejectNonExistentCustomer() {
    customerServiceProxy.setCustomerServiceUrl(String.format("http://%s:8080/customers/{customerId}", host));
    customerServiceProxy.verifyCustomerCustomerId(TestData.nonExistentCustomerId);
  }

}
