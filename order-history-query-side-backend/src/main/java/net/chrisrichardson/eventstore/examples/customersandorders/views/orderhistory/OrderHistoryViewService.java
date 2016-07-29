package net.chrisrichardson.eventstore.examples.customersandorders.views.orderhistory;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class OrderHistoryViewService {

  private CustomerViewRepository customerViewRepository;
  private OrderViewRepository orderViewRepository;
  private MongoTemplate mongoTemplate;

  @Autowired
  public OrderHistoryViewService(CustomerViewRepository customerViewRepository, OrderViewRepository orderViewRepository, MongoTemplate mongoTemplate) {
    this.customerViewRepository = customerViewRepository;
    this.orderViewRepository = orderViewRepository;
    this.mongoTemplate = mongoTemplate;
  }

  void createCustomer(String customerId, String customerName, Money creditLimit) {
    mongoTemplate.upsert(new Query(where("id").is(customerId)),
            new Update().set("name", customerName).set("creditLimit", creditLimit), CustomerView.class);
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
    mongoTemplate.upsert(new Query(where("id").is(customerId)),
            new Update().set("orders." + orderId, new OrderInfo(orderId, orderTotal)), CustomerView.class);


    mongoTemplate.upsert(new Query(where("id").is(orderId)),
            new Update().set("orderTotal", orderTotal), OrderView.class);
  }

  void approveOrder(String customerId, String orderId) {
    updateOrderStateInCustomerView(customerId, orderId, OrderState.APPROVED);

    updateOrderStateInOrderView(orderId, OrderState.APPROVED);

  }

  private void updateOrderStateInOrderView(String orderId, OrderState state) {
    mongoTemplate.updateFirst(new Query(where("id").is(orderId)),
            new Update().set("state", state), OrderView.class);
  }

  private void updateOrderStateInCustomerView(String customerId, String orderId, OrderState state) {
    mongoTemplate.upsert(new Query(where("id").is(customerId)),
            new Update().set("orders." + orderId + ".state", state), CustomerView.class);
  }

  void rejectOrder(String customerId, String orderId) {
    updateOrderStateInCustomerView(customerId, orderId, OrderState.REJECTED);
    updateOrderStateInOrderView(orderId, OrderState.REJECTED);
  }
}
