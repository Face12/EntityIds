package se.face.entityids.model.serialize;

import java.io.IOException;

import se.face.entityids.model.ValidID;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ValidIDJsonSerializer extends JsonSerializer<ValidID> {

	@Override
	public void serialize(ValidID validID, JsonGenerator gen,
			SerializerProvider serializers) throws IOException,
			JsonProcessingException {
		gen.writeStartObject();
		gen.writeStringField("type", validID.getClass().getCanonicalName());
		gen.writeStringField("value", validID.getOriginalId());
		gen.writeEndObject();
	}
}
