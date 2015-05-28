/**
 * 
 */
package se.face.entityids.model.serialize;

import java.io.IOException;

import se.face.entityids.model.ValidID;
import se.face.entityids.model.factory.IDFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author Samuel
 *
 */
public class ValidIDJsonDeserializer extends JsonDeserializer<ValidID> {

	@SuppressWarnings("unchecked")
	@Override
	public ValidID deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Class<ValidID> clazz = null;
		String value = null;
		Object[] objects;
		try {
			objects = new Object[]{readNextString(p), readNextString(p)};
			for (Object object: objects){
				if (object instanceof Class){
					clazz = (Class<ValidID>) object;
				}
				else if (object instanceof String){
					value = (String) object;
				}
			}
			return IDFactory.create(value, clazz);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	private Object readNextString(JsonParser p) throws Exception{
		String nextFieldName = p.nextFieldName();
		String nextFieldValue = p.nextTextValue();
		switch(nextFieldName){
		case "type":
			return Class.forName(nextFieldValue);
		case "value":
			return nextFieldValue;
		default:
			throw new RuntimeException(nextFieldName+ " invalid");
		}
	}

}
