package net.chrisrichardson.eventstore.examples.customersandorders.orders.commandsideservice.main;

import net.chrisrichardson.eventstore.examples.customersandorders.orders.commandsideservice.OrdersCommandSideServiceConfiguration;
import org.springframework.boot.SpringApplication;

public class OrdersCommandSideService {

    public static void main(String[] args) {
        SpringApplication.run(OrdersCommandSideServiceConfiguration.class, args);
    }
}
