package kafka.fundamentals.producer;

import kafka.fundamentals.producer.domain.CarPosition;
import kafka.fundamentals.producer.serializers.CustomJsonSerializer;
import kafka.fundamentals.producer.xmlparser.PositionParser;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.VoidSerializer;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

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
        try(var producer = createProducer()) {
            allPositionsStream().take(10)
                    .doOnNext(System.out::println)
                    .subscribe();

//            var record = new ProducerRecord<Void, Object>(TOPIC, CarProducerApp.getGreeting() + " ==> " + i);

        }



        Thread.sleep(4000);

//        try(var producer = createProducer()) {
//            for (int i = 0; i < 10; i++) {
//                var record = new ProducerRecord<Void, Object>(TOPIC, CarProducerApp.getGreeting() + " ==> " + i);
//                var metadata = producer.send(record).get();
//
//                var recordDesc = String.format("sent record(key=%s value=%s) meta(partition=%d, offset=%d)",
//                        record.key(),
//                        record.value(),
//                        metadata.partition(),
//                        metadata.offset());
//
//                System.out.println(recordDesc);
//            }
//        }
    }

    private static Producer<Void, Object> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomJsonSerializer.class.getName());
//        props.put(ProducerConfig.ACKS_CONFIG, "1");
        return new KafkaProducer<>(props);
    }
}
