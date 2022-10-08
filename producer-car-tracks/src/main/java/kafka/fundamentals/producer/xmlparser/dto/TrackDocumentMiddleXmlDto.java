package kafka.fundamentals.producer.xmlparser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class TrackDocumentMiddleXmlDto {

    private List<PositionXmlDto> positions;

    @JacksonXmlProperty(localName = "trkseg")
    public List<PositionXmlDto> getPositions() {
        return positions;
    }
}
