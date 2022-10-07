package kafka.fundamentals.producer.xmlparser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.ToString;

@JacksonXmlRootElement(localName = "trkpt")
@Getter
@ToString
public class PositionXmlDto {

    @JacksonXmlProperty(isAttribute = true, localName = "lat")
    private Double latitude;

    @JacksonXmlProperty(isAttribute = true, localName = "lon")
    private Double longitude;

    @JacksonXmlProperty(isAttribute = false, localName = "ele")
    private Double elevation;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private String time;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public String getTime() {
        return time;
    }
}
