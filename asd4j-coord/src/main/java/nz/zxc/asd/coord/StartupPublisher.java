package nz.zxc.asd.coord;

import nz.zxc.asd.schema.Event;
import nz.zxc.asd.schema.EventType;
import nz.zxc.asd.schema.Quality;
import nz.zxc.asd.schema.PublishReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupPublisher implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(StartupPublisher.class);

    private final EventPublisher publisher;

    public StartupPublisher(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Publishing startup test event...");

        Event event = Event.builder()
                .eventType(EventType.TELEMETRY)
                .path("/dev/office/temperature")
                .siteId("dev-local")
                .value(21.5)
                .unit("degC")
                .quality(Quality.OK)
                .publishReason(PublishReason.HEARTBEAT)
                .build();

        publisher.publish("asd4j.telemetry", event);

        log.info("Startup event published.");
    }
}
