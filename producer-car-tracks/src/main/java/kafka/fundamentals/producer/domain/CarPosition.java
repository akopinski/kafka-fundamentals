package kafka.fundamentals.producer.domain;

import java.time.LocalDateTime;

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

    public String getCarId() {
        return carId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CarPosition{" +
                "carId='" + carId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", time=" + time +
                '}';
    }
}
