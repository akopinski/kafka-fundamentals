package kafka.fundamentals.producer.xmlparser.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class TrackDocumentMiddleXmlDto {

    @JacksonXmlProperty(localName = "trkseg")
    private List<PositionXmlDto> positions;

    public List<PositionXmlDto> getPositions() {
        return positions;
    }
}
