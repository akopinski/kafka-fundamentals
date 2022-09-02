package kafka.fundamentals.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.VoidDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDbApp {
    private final static String TOPIC = "car-tracks-raw";
    private final static String BOOTSTRAP_SERVERS = "localhost:9093,localhost:9094";

    public static void main(String[] args) {

        try (var consumer = createConsumer()) {
            // Subscribe to the topic.
            consumer.subscribe(Collections.singletonList(TOPIC));
            startAtBeginning(consumer);

            for (int i = 0; i < 10; i++) {
                final ConsumerRecords<Void, String> consumerRecords = consumer.poll(Duration.ofMillis(200));

                System.out.printf("Received %d records%n", consumerRecords.count());

                consumerRecords.forEach(record -> {
                    var recordsDesc = String.format("Consumer Record:(%s, %s, %d, %d)",
                            record.key(), record.value(),
                            record.partition(), record.offset());
                    System.out.println(recordsDesc);
                });
            }
        }

    }

    private static void startAtBeginning(Consumer<Void, String> consumer) {
        consumer.poll(Duration.ZERO);
        consumer.seekToBeginning(consumer.assignment());
    }

    private static Consumer<Void, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //TODO!!!
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Create the consumer using props.
        return new KafkaConsumer<>(props);
    }
}
