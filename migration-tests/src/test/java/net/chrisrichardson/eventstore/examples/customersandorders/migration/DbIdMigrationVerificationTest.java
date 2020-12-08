package net.chrisrichardson.eventstore.examples.customersandorders.migration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DbIdMigrationVerificationTest.Config.class)
public class DbIdMigrationVerificationTest {

  @Configuration
  @EnableAutoConfiguration
  public static class Config {}

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  //after first call of e2e tests (before migration), events should have ids, after second call (after migration) don't
  public void testThatEventsAreMigrated() {
    List<Map<String, Object>> eventsWithEmptyId =
            jdbcTemplate.queryForList("select * from eventuate.events where event_type <> 'CDC-IGNORED' and event_id = ''");

    List<Map<String, Object>> eventsWithNotEmptyId =
            jdbcTemplate.queryForList("select * from eventuate.events where event_type <> 'CDC-IGNORED' and event_id <> ''");

    Assert.assertTrue(eventsWithEmptyId.size() > 0);
    Assert.assertEquals(eventsWithEmptyId.size(), eventsWithNotEmptyId.size());

    verifyEvents(eventsWithEmptyId);
    verifyEvents(eventsWithNotEmptyId);
  }

  private void verifyEvents(List<Map<String, Object>> events) {
    final Map<String, List<String>> eventTypeRequiredFields = new HashMap<>();

    eventTypeRequiredFields.put("net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreatedEvent", Arrays.asList("name", "creditLimit"));
    eventTypeRequiredFields.put("net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderCreatedEvent", Arrays.asList("orderTotal", "customerId"));
    eventTypeRequiredFields.put("net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreditReservedEvent", Arrays.asList("orderTotal", "orderId"));
    eventTypeRequiredFields.put("net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderApprovedEvent", Arrays.asList("customerId"));
    eventTypeRequiredFields.put("net.chrisrichardson.eventstore.examples.customersandorders.orders.events.OrderRejectedEvent", Arrays.asList("customerId"));
    eventTypeRequiredFields.put("net.chrisrichardson.eventstore.examples.customersandorders.customers.events.CustomerCreditLimitExceededEvent", Arrays.asList("orderId"));

    eventTypeRequiredFields.forEach((event, fields) -> verifyEvent(events, event, fields));
  }

  private void verifyEvent(List<Map<String, Object>> events, String eventType, List<String> requiredFields) {
    List<Map<String, Object>> targetEvents = findEventOfType(events, eventType);

    Assert.assertTrue(String.format("event of type %s should be present", eventType), targetEvents.size() > 0);

    for (Map<String, Object> event : targetEvents) {
      String eventData = (String)event.get("event_data");

      checkThatRequiredFieldsArePresent(eventData, eventType, requiredFields);
    }
  }

  private List<Map<String, Object>> findEventOfType(List<Map<String, Object>> events, String eventType) {
    return events
            .stream()
            .filter(event -> eventType.equals(event.get("event_type")))
            .collect(Collectors.toList());
  }

  private void checkThatRequiredFieldsArePresent(String eventData, String eventType, List<String> requiredFields) {
    requiredFields
            .forEach(field ->
                    Assert.assertTrue(String.format("event data \"%s\" of event type \"%s\" should contain \"%s\" field", eventData, eventType, field),
                            eventData.contains(field)));
  }
}
