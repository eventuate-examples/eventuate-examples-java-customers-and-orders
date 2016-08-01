package net.chrisrichardson.eventstore.examples.customersandorders.orders.history.querysideservice.main;

import net.chrisrichardson.eventstore.examples.customersandorders.orders.history.querysideservice.OrderHistoryQuerySideServiceConfiguration;
import org.springframework.boot.SpringApplication;

public class OrderHistoryQuerySideService {

    public static void main(String[] args) {
        SpringApplication.run(OrderHistoryQuerySideServiceConfiguration.class, args);
    }
}
