package net.chrisrichardson.eventstore.examples.customersandorders.ordersservice.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CustomerServiceProxyIntegrationTestConfiguration.class,
        webEnvironment= SpringBootTest.WebEnvironment.NONE,
        properties={"customer.service.url=http://localhost:8080"}
)
@AutoConfigureStubRunner(ids =
        {"net.chrisrichardson.eventstore.examples.customersandorders:common-contracts:+:stubs:8080"}
//        repositoryRoot = "file:///Users/cer/.m2/repository"
)
@DirtiesContext
public class CustomerServiceProxyWireMockBasedIntegrationTest {

  @Autowired
  private CustomerServiceProxy customerServiceProxy;

  @Test
  public void shouldVerifyExistingCustomer() {
    customerServiceProxy.setCustomerServiceUrl("http://localhost:8080/customers/{customerId}");
    customerServiceProxy.verifyCustomerCustomerId(TestData.customerId);
  }

  @Test(expected = CustomerNotFoundException.class)
  public void shouldRejectNonExistentCustomer() {
    customerServiceProxy.setCustomerServiceUrl("http://localhost:8080/customers/{customerId}");
    customerServiceProxy.verifyCustomerCustomerId(TestData.nonExistentCustomerId);
  }

}
