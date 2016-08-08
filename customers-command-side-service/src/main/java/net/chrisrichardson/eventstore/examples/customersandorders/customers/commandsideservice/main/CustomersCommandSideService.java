package net.chrisrichardson.eventstore.examples.customersandorders.customers.commandsideservice.main;

import net.chrisrichardson.eventstore.examples.customersandorders.customers.commandsideservice.CustomersCommandSideServiceConfiguration;
import org.springframework.boot.SpringApplication;

public class CustomersCommandSideService {
    public static void main(String[] args) {
        SpringApplication.run(CustomersCommandSideServiceConfiguration.class, args);
    }
}
