package kafka.fundamentals.producer.xmlparser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kafka.fundamentals.producer.domain.CarPosition;
import kafka.fundamentals.producer.xmlparser.dto.TrackDocumentTopXmlDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class PositionParser {

    private DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    private DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    protected static XmlMapper mapper = XmlMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .addModule(new JavaTimeModule())
            .build();

    public TrackDocumentTopXmlDto parseDocument(String pathToFile) throws IOException {
        var inputStream = this.getClass().getResourceAsStream(pathToFile);

        return mapper.readValue(inputStream, TrackDocumentTopXmlDto.class);
    }

    public LocalDateTime timeParser(String time) {
        LocalDateTime result = null;

        try {
            result = LocalDateTime.parse(time, dateTimeFormatter1);
        } catch (DateTimeParseException ex) {
            result = LocalDateTime.parse(time, dateTimeFormatter2);
        }

        return result;
    }

    public List<CarPosition> parsedPositions(String carId, String pathToFile) throws IOException {
        var wholeDocument = parseDocument(pathToFile);

        return wholeDocument.getMiddle().getPositions().stream().map(pos ->
                new CarPosition(carId, pos.getLatitude(), pos.getLongitude(), pos.getElevation(), timeParser(pos.getTime()))
        ).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        var pp = new PositionParser();
        var result = pp.parsedPositions("aa11","/tracks/track2.gpx.xml");

        System.out.println(result.subList(1, 6));
        System.out.println(result.size());

    }

}
