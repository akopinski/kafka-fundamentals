package kafka.fundamentals.producer.xmlparser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.ToString;

@Getter
@JacksonXmlRootElement(localName = "gpx")
@ToString
public class TrackDocumentTopXmlDto {

    private String creator;

    @JacksonXmlProperty(localName = "trk")
    private TrackDocumentMiddleXmlDto middle;

    public TrackDocumentMiddleXmlDto getMiddle() {
        return middle;
    }
}
