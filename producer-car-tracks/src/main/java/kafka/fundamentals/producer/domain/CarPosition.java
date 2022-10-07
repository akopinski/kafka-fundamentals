package kafka.fundamentals.producer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CarPosition {

    private String carId;

    private Double latitude;

    private Double longitude;

    private Double elevation;

    private LocalDateTime time;

    public CarPosition(String carId, Double latitude, Double longitude, Double elevation, LocalDateTime time) {
        this.carId = carId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.time = time;
    }
}
