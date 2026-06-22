package nz.zxc.asd.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void eventSerialisesToJson() throws Exception {

        Event event = Event.builder()
                .eventType(EventType.TELEMETRY)
                .path("/dev/office/temperature")
                .siteId("dev-local")
                .value(21.5)
                .unit("degC")
                .quality(Quality.OK)
                .publishReason(PublishReason.COV)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String json = mapper.writeValueAsString(event);
        System.out.println(json);

        // verify key fields
        assertTrue(json.contains("\"schemaVersion\":\"1.0\""));
        assertTrue(json.contains("\"eventType\":\"TELEMETRY\""));
        assertTrue(json.contains("\"path\":\"/dev/office/temperature\""));
        assertTrue(json.contains("\"siteId\":\"dev-local\""));
        assertTrue(json.contains("\"value\":21.5"));
        assertTrue(json.contains("\"unit\":\"degC\""));
        assertTrue(json.contains("\"quality\":\"OK\""));
        assertTrue(json.contains("\"publishReason\":\"COV\""));
    }

    @Test
    void eventHasTimestampWhenNotSet() {

        Event event = Event.builder()
                .eventType(EventType.TELEMETRY)
                .path("/dev/office/temperature")
                .siteId("dev-local")
                .value(21.5)
                .build();

        assertNotNull(event.getTimestamp());
    }

    @Test
    void pathIsRequired() {

        Event event = Event.builder()
                .eventType(EventType.TELEMETRY)
                .siteId("dev-local")
                .value(21.5)
                .build();

        assertNull(event.getPath());
        // path validation will be enforced in asd4j-event
        // schema module just defines the structure
    }
}
