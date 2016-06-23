package net.chrisrichardson.eventstore.examples.customersandorders.commontest;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderInfo;
import net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory.OrderView;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

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

  protected <T> T await(Observable<T> obs) {
    return obs.toBlocking().first();
  }

  protected abstract CustomerView getCustomerView(String customerId);

  protected abstract OrderView getOrderView(String orderId);

  protected abstract String createOrder(String customerId, Money orderTotal);

  protected abstract String createCustomer(Money creditLimit);

  private <T> T eventually(Supplier<T> producer, Predicate<T> predicate) {
    return eventuallyAsync(() -> Observable.just(producer.get()), predicate);
  }

  private <T> T eventuallyAsync(Supplier<Observable<T>> producer, Predicate<T> predicate) {
    try {
      return Observable.interval(1000, TimeUnit.MILLISECONDS)
              .take(10)
              .flatMap(idx -> producer.get())
              .filter(predicate::test)
              .toBlocking().first();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
