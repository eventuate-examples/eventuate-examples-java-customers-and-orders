package net.chrisrichardson.eventstore.examples.customersandorders.migration;

import io.eventuate.Event;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreditLimitExceededEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreditReservedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderApprovedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderCreatedEvent;
import net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderRejectedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DbIdMigrationVerificationTest.Config.class)
public class DbIdMigrationVerificationTest {

  @Configuration
  @EnableAutoConfiguration
  public static class Config {}

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final Map<String, List<String>> eventTypeRequiredFields = new HashMap<>();

  @Test
  //after first call of e2e tests (before migration), events should have ids, after second call (after migration) don't
  public void testThatEventsAreMigrated() {
    List<Map<String, Object>> eventsWithEmptyId =
            jdbcTemplate.queryForList("select * from eventuate.events where event_type <> 'CDC-IGNORED' and event_id = ''");

    List<Map<String, Object>> eventsWithNotEmptyId =
            jdbcTemplate.queryForList("select * from eventuate.events where event_type <> 'CDC-IGNORED' and event_id <> ''");

    assertTrue(eventsWithEmptyId.size() > 0);
    assertEquals(eventsWithEmptyId.size(), eventsWithNotEmptyId.size());

    assertEventsHaveRequiredFields(eventsWithEmptyId);
    assertEventsHaveRequiredFields(eventsWithNotEmptyId);
  }

  @BeforeEach
  public void initializeEventTypeRequiredFields() {
    addEventFieldsToEventTypeRequiredFields(CustomerCreatedEvent.class, "name", "creditLimit");
    addEventFieldsToEventTypeRequiredFields(CustomerCreditReservedEvent.class, "orderId", "orderTotal");
    addEventFieldsToEventTypeRequiredFields(CustomerCreditLimitExceededEvent.class, "orderId");
    addEventFieldsToEventTypeRequiredFields(OrderCreatedEvent.class, "orderTotal", "customerId");
    addEventFieldsToEventTypeRequiredFields(OrderApprovedEvent.class, "customerId");
    addEventFieldsToEventTypeRequiredFields(OrderRejectedEvent.class, "customerId");
  }

  private void addEventFieldsToEventTypeRequiredFields(Class<? extends Event> eventClass, String... fields) {
    eventTypeRequiredFields.put(eventClass.getName(), Arrays.asList(fields));
  }

  private void assertEventsHaveRequiredFields(List<Map<String, Object>> events) {
    events.forEach(event -> {
      String eventType = (String)event.get("event_type");
      List<String> requiredFields = eventTypeRequiredFields.get(eventType);
      String eventData = (String)event.get("event_data");

      String assertMessageTemplate = "event data \"%s\" of event type \"%s\" should contain \"%s\" field";

      requiredFields
              .forEach(field ->
                      assertTrue(eventData.contains(field),
                              String.format(assertMessageTemplate, eventData, eventType, field)));
    });
  }
}
