package net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.web.orders;

import net.chrisrichardson.eventstore.examples.customersandorders.ordershistorycommon.OrderView;
import net.chrisrichardson.eventstore.examples.customersandorders.ordershistoryviewservice.backend.OrderViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderViewController {

  private OrderViewRepository orderViewRepository;

  @Autowired
  public OrderViewController(OrderViewRepository orderViewRepository) {
    this.orderViewRepository = orderViewRepository;
  }


  @RequestMapping(value="/orders/{orderId}", method= RequestMethod.GET)
  public OrderView getOrder(@PathVariable String orderId) {

    OrderView ov = orderViewRepository.findOne(orderId);
    if (ov != null)
      return ov;
    else
      throw new UnsupportedOperationException();
  }


}
