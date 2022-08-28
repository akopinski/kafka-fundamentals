package kafka.fundamentals.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.VoidSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerApp {
    private final static String TOPIC = "hello";
    private final static String BOOTSTRAP_SERVERS = "localhost:9093,localhost:9094";

    public static String getGreeting() {
        return "Hello!";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(var producer = createProducer()) {
            for (int i = 0; i < 10; i++) {
                var record = new ProducerRecord<Void, String>(TOPIC, ProducerApp.getGreeting() + " ==> " + i);
                var metadata = producer.send(record).get();

                var recordDesc = String.format("sent record(key=%s value=%s) meta(partition=%d, offset=%d)",
                        record.key(),
                        record.value(),
                        metadata.partition(),
                        metadata.offset());

                System.out.println(recordDesc);
            }
        }
    }

    private static Producer<Void, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.put(ProducerConfig.ACKS_CONFIG, "1");
        return new KafkaProducer<>(props);
    }
}
