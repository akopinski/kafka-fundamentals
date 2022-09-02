package kafka.fundamentals.consumer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarPosition {

    private String carId;

    private Double latitude;

    private Double longitude;

    private Double elevation;

    private LocalDateTime time;

}
