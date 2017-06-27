package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class OrderHistoryViewService {

  private CustomerViewRepository customerViewRepository;
  private OrderViewRepository orderViewRepository;

  @Autowired
  public OrderHistoryViewService(CustomerViewRepository customerViewRepository, OrderViewRepository orderViewRepository, MongoTemplate mongoTemplate) {
    this.customerViewRepository = customerViewRepository;
    this.orderViewRepository = orderViewRepository;
  }

  public void createCustomer(String customerId, String customerName, Money creditLimit) {
    customerViewRepository.addCustomer(customerId, customerName, creditLimit);
  }

  public void addOrder(String customerId, String orderId, Money orderTotal) {
    customerViewRepository.addOrder(customerId, orderId, orderTotal);
    orderViewRepository.addOrder(orderId, orderTotal);
  }

  public void approveOrder(String customerId, String orderId) {
    customerViewRepository.updateOrderState(customerId, orderId, OrderState.APPROVED);
    orderViewRepository.updateOrderState(orderId, OrderState.APPROVED);
  }

  public void rejectOrder(String customerId, String orderId) {
    customerViewRepository.updateOrderState(customerId, orderId, OrderState.REJECTED);
    orderViewRepository.updateOrderState(orderId, OrderState.REJECTED);
  }
}
