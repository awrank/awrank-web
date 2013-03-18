package com.awrank.web.model.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;

import java.io.IOException;

/**
 * @author Alex Polyakov
 */
public class MyXmlLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
//	static {
//		BasicSerializerFactory.Config
//		JsonSerializableWithType t=new ObjectNode();
//		ObjectMapper om=new ObjectMapper(new JsonFactory());
//		om
//	}

	@Override
	public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeNumber(value.toDate().getTime());
	}

}