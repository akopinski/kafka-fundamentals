package kafka.fundamentals.producer.xmlparser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "gpx")
public class TrackDocumentTopXmlDto {

    private String creator;

    private TrackDocumentMiddleXmlDto middle;

    @JacksonXmlProperty(localName = "trk")
    public TrackDocumentMiddleXmlDto getMiddle() {
        return middle;
    }

    public String getCreator() {
        return creator;
    }
}
