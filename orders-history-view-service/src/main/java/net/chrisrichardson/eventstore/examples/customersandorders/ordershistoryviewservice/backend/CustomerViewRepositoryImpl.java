package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend;

import net.chrisrichardson.eventstore.examples.customersandorders.common.domain.Money;
import net.chrisrichardson.eventstore.examples.customersandorders.common.order.OrderState;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.CustomerView;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class CustomerViewRepositoryImpl implements CustomerViewRepositoryCustom {

  private MongoTemplate mongoTemplate;

  @Autowired
  public CustomerViewRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void addCustomer(String customerId, String customerName, Money creditLimit) {
    mongoTemplate.upsert(new Query(where("id").is(customerId)),
            new Update().set("name", customerName).set("creditLimit", creditLimit), CustomerView.class);
  }

  @Override
  public void addOrder(String customerId, String orderId, Money orderTotal) {
    mongoTemplate.upsert(new Query(where("id").is(customerId)),
            new Update().set("orders." + orderId, new OrderInfo(orderId, orderTotal)), CustomerView.class);
  }

  @Override
  public void updateOrderState(String customerId, String orderId, OrderState state) {
    mongoTemplate.upsert(new Query(where("id").is(customerId)),
            new Update().set("orders." + orderId + ".state", state), CustomerView.class);
  }
}
