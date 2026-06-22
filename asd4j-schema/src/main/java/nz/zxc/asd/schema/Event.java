package nz.zxc.asd.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    private String schemaVersion = "1.0";
    private EventType eventType;
    private String path;
    private String siteId;
    private Object value;
    private String unit;
    private Quality quality;
    private PublishReason publishReason;
    private Instant timestamp;

    // private constructor — use builder
    private Event() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Event event = new Event();

        public Builder eventType(EventType eventType) {
            event.eventType = eventType;
            return this;
        }

        public Builder path(String path) {
            event.path = path;
            return this;
        }

        public Builder siteId(String siteId) {
            event.siteId = siteId;
            return this;
        }

        public Builder value(Object value) {
            event.value = value;
            return this;
        }

        public Builder unit(String unit) {
            event.unit = unit;
            return this;
        }

        public Builder quality(Quality quality) {
            event.quality = quality;
            return this;
        }

        public Builder publishReason(PublishReason publishReason) {
            event.publishReason = publishReason;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            event.timestamp = timestamp;
            return this;
        }

        public Event build() {
            if (event.timestamp == null) {
                event.timestamp = Instant.now();
            }
            return event;
        }
    }

    // getters
    public String getSchemaVersion()       { return schemaVersion; }
    public EventType getEventType()        { return eventType; }
    public String getPath()                { return path; }
    public String getSiteId()              { return siteId; }
    public Object getValue()               { return value; }
    public String getUnit()                { return unit; }
    public Quality getQuality()            { return quality; }
    public PublishReason getPublishReason(){ return publishReason; }
    public Instant getTimestamp()          { return timestamp; }
}
