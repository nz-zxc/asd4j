package nz.zxc.asd.coord;

import nz.zxc.asd.schema.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(EventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, Event event) {
        kafkaTemplate.send(topic, event.getPath(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish event to {}: {}",
                                topic, ex.getMessage());
                    } else {
                        log.info("Published event → topic={} path={} type={}",
                                topic,
                                event.getPath(),
                                event.getEventType());
                    }
                });
    }
}
