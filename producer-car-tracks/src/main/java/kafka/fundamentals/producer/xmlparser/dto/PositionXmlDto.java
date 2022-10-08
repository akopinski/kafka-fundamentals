package kafka.fundamentals.producer.xmlparser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "trkpt")
public class PositionXmlDto {

    private Double latitude;

    private Double longitude;

    private Double elevation;

    private String time;

    @JacksonXmlProperty(isAttribute = true, localName = "lat")
    public Double getLatitude() {
        return latitude;
    }

    @JacksonXmlProperty(isAttribute = true, localName = "lon")
    public Double getLongitude() {
        return longitude;
    }

    @JacksonXmlProperty(isAttribute = false, localName = "ele")
    public Double getElevation() {
        return elevation;
    }

    public String getTime() {
        return time;
    }
}
