package kafka.fundamentals.producer;

import kafka.fundamentals.producer.domain.CarPosition;
import kafka.fundamentals.producer.serializers.CustomJsonSerializer;
import kafka.fundamentals.producer.xmlparser.PositionParser;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CarProducerApp {
    private final static String TOPIC = "car-tracks-raw";
    private final static String BOOTSTRAP_SERVERS = "localhost:9093,localhost:9094";

    public static Flux<CarPosition> allPositionsStream() throws IOException {
        var positionParser = new PositionParser();

        Flux<CarPosition> car1Stream = Flux.fromIterable(positionParser.parsedPositions("aa11", "/tracks/track1.gpx.xml"))
                .delayElements(Duration.ofMillis(100));
        Flux<CarPosition> car2Stream = Flux.fromIterable(positionParser.parsedPositions("bb22", "/tracks/track2.gpx.xml"))
                .delayElements(Duration.ofMillis(200));
        Flux<CarPosition> car3Stream = Flux.fromIterable(positionParser.parsedPositions("cc33", "/tracks/track3.gpx.xml"))
                .delayElements(Duration.ofMillis(50));

        return Flux.merge(car1Stream, car2Stream, car3Stream);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        try (var producer = createProducer()) {
            allPositionsStream()
                    .doOnNext(System.out::println)
                    .subscribe(position -> {
                        var record = new ProducerRecord<String, Object>(TOPIC, position.getCarId(), position);

                        try {
                            producer.send(record).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });


            Thread.sleep(7000);
        }
    }

    private static Producer<String, Object> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomJsonSerializer.class.getName());

        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.RETRIES_CONFIG, "100");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "3");

        props.setProperty("schema.registry.url", "http://127.0.0.1:8081");
        props.setProperty("specific.avro.reader", "true");

        return new KafkaProducer<>(props);
    }
}
