package nz.zxc.asd.coord;

import nz.zxc.asd.schema.Event;
import nz.zxc.asd.schema.EventType;
import nz.zxc.asd.schema.Quality;
import nz.zxc.asd.schema.PublishReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StartupPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(StartupPublisher.class);

    private final EventPublisher publisher;

    public StartupPublisher(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedRate = 30000, initialDelay = 1000)
    public void scan() {

        log.debug("Scan cycle starting...");

        publisher.publish("asd4j.telemetry", Event.builder()
                .eventType(EventType.TELEMETRY)
                .path("/dev/office/temperature")
                .siteId("dev-local")
                .value(21.5)
                .unit("degC")
                .quality(Quality.OK)
                .publishReason(PublishReason.HEARTBEAT)
                .build());

        publisher.publish("asd4j.telemetry", Event.builder()
                .eventType(EventType.TELEMETRY)
                .path("/dev/office/co2")
                .siteId("dev-local")
                .value(850)
                .unit("ppm")
                .quality(Quality.OK)
                .publishReason(PublishReason.HEARTBEAT)
                .build());

        publisher.publish("asd4j.telemetry", Event.builder()
                .eventType(EventType.TELEMETRY)
                .path("/dev/office/lux")
                .siteId("dev-local")
                .value(420)
                .unit("lux")
                .quality(Quality.OK)
                .publishReason(PublishReason.HEARTBEAT)
                .build());

        log.debug("Scan cycle complete — 3 points published.");
    }
}