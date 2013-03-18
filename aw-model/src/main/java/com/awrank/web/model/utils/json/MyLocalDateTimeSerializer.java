package com.awrank.web.model.utils.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.LocalDateTime;

import java.io.IOException;

/**
 * @author Alex Polyakov
 */
public class MyLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeNumber(value.toDate().getTime());
	}
}