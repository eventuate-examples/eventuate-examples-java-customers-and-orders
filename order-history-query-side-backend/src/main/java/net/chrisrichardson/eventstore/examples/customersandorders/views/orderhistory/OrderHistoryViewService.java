package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import org.springframework.beans.factory.annotation.Autowired;

// TODO - this needs work to deal with concurrent updates
// TODO - or replace with in-place updates


public class OrderHistoryViewService {

  private CustomerViewRepository customerViewRepository;
  private OrderViewRepository orderViewRepository;

  @Autowired
  public OrderHistoryViewService(CustomerViewRepository customerViewRepository, OrderViewRepository orderViewRepository) {
    this.customerViewRepository = customerViewRepository;
    this.orderViewRepository = orderViewRepository;
  }

  void createCustomer(String customerId, String customerName, Money creditLimit) {
    CustomerView customerView = findOrCreateCustomerView(customerId);
    customerView.setName(customerName);
    customerView.setCreditLimit(creditLimit);
    customerViewRepository.save(customerView);
  }

  private CustomerView findOrCreateCustomerView(String customerId) {
    CustomerView customerView = customerViewRepository.findOne(customerId);
    if (customerView == null) {
      customerView = new CustomerView();
      customerView.setId(customerId);
      customerView = customerViewRepository.insert(customerView);
    }
    return customerView;
  }

  void addOrder(String customerId, String orderId, Money orderTotal) {
    CustomerView customerView = findOrCreateCustomerView(customerId);
    customerView.addOrder(orderId, orderTotal);
    customerViewRepository.save(customerView);

    OrderView orderView = new OrderView(orderId, orderTotal);
    orderViewRepository.save(orderView);
  }

  void approveOrder(String customerId, String orderId) {
    CustomerView customerView = findOrCreateCustomerView(customerId);
    customerView.approveOrder(orderId);
    customerViewRepository.save(customerView);

    OrderView orderView = orderViewRepository.findOne(orderId);
    orderView.approve();
    orderViewRepository.save(orderView);
  }

  void rejectOrder(String customerId, String orderId) {
    CustomerView customerView = findOrCreateCustomerView(customerId);
    customerView.rejectOrder(orderId);
    customerViewRepository.save(customerView);

    OrderView orderView = orderViewRepository.findOne(orderId);
    orderView.reject();
    orderViewRepository.save(orderView);
  }
}
