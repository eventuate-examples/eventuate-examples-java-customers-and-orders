package net.chrisrichardson.eventstore.examples.customersandorders.commontest;


import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderInfo;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderView;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;
import static net.chrisrichardson.eventstore.examples.customersandorders.commontest.TestUtil.eventually;

public abstract class AbstractCustomerAndOrdersIntegrationTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final Money creditLimit = new Money(1000);

    @Test
    public void shouldCreateAndApproveOrder() {

        String customerId = createCustomer(creditLimit);

        Money orderTotal = new Money(720);

        String orderId = createOrder(customerId, orderTotal);

        eventually(() -> getOrderView(orderId), o -> o != null && o.getState() == OrderState.APPROVED);

        CustomerView customerView = eventually(() -> getCustomerView(customerId), cv -> {
            OrderInfo orderInfo = cv.getOrders().get(orderId);
            return orderInfo != null && orderInfo.getState() == OrderState.APPROVED;
        });

        assertEquals(creditLimit, customerView.getCreditLimit());
        assertEquals(orderTotal, customerView.getOrders().get(orderId).getOrderTotal());

    }

    @Test
    public void shouldCreateAndRejectOrder() {

        String customerId = createCustomer(creditLimit);

        Money orderTotal = creditLimit.add(new Money(1));

        String orderId = createOrder(customerId, orderTotal);

        eventually(() -> getOrderView(orderId), o -> o != null && o.getState() == OrderState.REJECTED);

        CustomerView customerView = eventually(() -> getCustomerView(customerId), cv -> {
            OrderInfo orderInfo = cv.getOrders().get(orderId);
            return orderInfo != null && orderInfo.getState() == OrderState.REJECTED;
        });

        assertEquals(creditLimit, customerView.getCreditLimit());
        assertEquals(orderTotal, customerView.getOrders().get(orderId).getOrderTotal());

    }

    protected abstract CustomerView getCustomerView(String customerId);

    protected abstract OrderView getOrderView(String orderId);

    protected abstract String createOrder(String customerId, Money orderTotal);

    protected abstract String createCustomer(Money creditLimit);
}
