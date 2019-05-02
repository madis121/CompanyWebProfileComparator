package ee.tlu.cwpc.serializer;

import java.io.IOException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateTimeSerializer extends JsonSerializer<DateTime> {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");

  @Override
  public void serialize(DateTime value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(FORMATTER.print(value));
  }

}
