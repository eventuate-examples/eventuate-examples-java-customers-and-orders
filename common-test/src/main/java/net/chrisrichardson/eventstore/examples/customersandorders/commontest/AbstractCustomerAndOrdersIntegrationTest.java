package net.chrisrichardson.eventstore.examples.customersandorders.commontest;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderInfo;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderView;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static io.eventuate.util.test.async.Eventually.eventually;
import static io.eventuate.util.test.async.Eventually.eventuallyReturning;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public abstract class AbstractCustomerAndOrdersIntegrationTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final Money creditLimit = new Money(1000);

    protected class IntegrationTestCustomerNotFoundException extends RuntimeException {
        public IntegrationTestCustomerNotFoundException(Throwable cause) {
            super(cause);
        }
    }

    @Test
    public void shouldCreateAndApproveOrder() {

        String customerId = createCustomer(creditLimit);

        Money orderTotal = new Money(720);

        String orderId = createOrder(customerId, orderTotal);

        eventually(40, 500, TimeUnit.MILLISECONDS, () -> {
                OrderView o = getOrderView(orderId);
                assertNotNull(o);
                assertEquals(OrderState.APPROVED, o.getState());
        });

        CustomerView customerView = eventuallyReturning(() -> {
          CustomerView cv = getCustomerView(customerId);
          assertNotNull(cv);
          OrderInfo orderInfo = cv.getOrders().get(orderId);
          assertNotNull(orderInfo);
          assertEquals(OrderState.APPROVED, orderInfo.getState());
          return cv;
        });

        assertEquals(creditLimit, customerView.getCreditLimit());
        assertEquals(orderTotal, customerView.getOrders().get(orderId).getOrderTotal());

    }

    @Test
    public void shouldCreateAndRejectOrder() {

        String customerId = createCustomer(creditLimit);

        Money orderTotal = creditLimit.add(new Money(1));

        String orderId = createOrder(customerId, orderTotal);

        eventually(40, 500, TimeUnit.MILLISECONDS, () -> {
          OrderView o = getOrderView(orderId);
          assertNotNull(o);
          assertEquals(OrderState.REJECTED, o.getState());
        });

        CustomerView customerView = eventuallyReturning(() -> {
          CustomerView cv = getCustomerView(customerId);
          assertNotNull(cv);
          OrderInfo orderInfo = cv.getOrders().get(orderId);
          assertNotNull(orderInfo);
          assertEquals(OrderState.REJECTED, orderInfo.getState());
          return cv;
        });

        assertEquals(creditLimit, customerView.getCreditLimit());
        assertEquals(orderTotal, customerView.getOrders().get(orderId).getOrderTotal());

    }

    @Test
    public void shouldRejectOrderWithInvalidCustomerId() {

        Money orderTotal = new Money(720);

        try {
            createOrder("unknown-customer-id", orderTotal);
            fail();
        } catch (IntegrationTestCustomerNotFoundException e) {
            // Expected
        }
    }

    protected abstract CustomerView getCustomerView(String customerId);

    protected abstract OrderView getOrderView(String orderId);

    protected abstract String createOrder(String customerId, Money orderTotal);

    protected abstract String createCustomer(Money creditLimit);
}
