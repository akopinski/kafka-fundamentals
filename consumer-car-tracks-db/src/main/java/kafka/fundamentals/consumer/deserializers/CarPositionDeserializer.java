package kafka.fundamentals.consumer.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kafka.fundamentals.consumer.domain.CarPosition;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CarPositionDeserializer implements Deserializer<CarPosition> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public CarPosition deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            return objectMapper.readValue(new String(data, "UTF-8"), CarPosition.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto: "+e);
        }
    }

    @Override
    public void close() {
    }
}
