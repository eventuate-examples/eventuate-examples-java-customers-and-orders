package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.service;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.OrderState;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain.CustomerViewRepository;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.domain.OrderViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class OrderHistoryViewService {

  private CustomerViewRepository customerViewRepository;
  private OrderViewRepository orderViewRepository;

  @Autowired
  public OrderHistoryViewService(CustomerViewRepository customerViewRepository, OrderViewRepository orderViewRepository) {
    this.customerViewRepository = customerViewRepository;
    this.orderViewRepository = orderViewRepository;
  }

  @Retryable(
          value = { DuplicateKeyException.class },
          maxAttempts = 4,
          backoff = @Backoff(delay = 250))
  public void createCustomer(String customerId, String customerName, Money creditLimit) {
    customerViewRepository.addCustomer(customerId, customerName, creditLimit);
  }

  @Retryable(
          value = { DuplicateKeyException.class },
          maxAttempts = 4,
          backoff = @Backoff(delay = 250))
  public void addOrder(String customerId, String orderId, Money orderTotal) {
    customerViewRepository.addOrder(customerId, orderId, orderTotal);
    orderViewRepository.addOrder(orderId, orderTotal);
  }

  @Retryable(
          value = { DuplicateKeyException.class },
          maxAttempts = 4,
          backoff = @Backoff(delay = 250))
  public void approveOrder(String customerId, String orderId) {
    customerViewRepository.updateOrderState(customerId, orderId, OrderState.APPROVED);
    orderViewRepository.updateOrderState(orderId, OrderState.APPROVED);
  }

  @Retryable(
          value = { DuplicateKeyException.class },
          maxAttempts = 4,
          backoff = @Backoff(delay = 250))
  public void rejectOrder(String customerId, String orderId) {
    customerViewRepository.updateOrderState(customerId, orderId, OrderState.REJECTED);
    orderViewRepository.updateOrderState(orderId, OrderState.REJECTED);
  }
}
