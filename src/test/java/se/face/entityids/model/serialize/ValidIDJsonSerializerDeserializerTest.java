/**
 * 
 */
package se.face.entityids.model.serialize;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;
import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.PersonalIdentityNumberSE;

/**
 * @author Samuel Edqvist
 *
 */
public class ValidIDJsonSerializerDeserializerTest {
	private static final String testPnr = "194301013787";
	
	@Test
	public void shouldSerialize() throws JsonProcessingException, IOException, InvalidIdException{
		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		mapper.writeValue(stringWriter, testClass());
		String value = stringWriter.getBuffer().toString();
		assertEquals(json(), value);
	}

	@Test
	public void shouldDeserialize() throws JsonProcessingException, IOException, InvalidIdException{
		ObjectMapper mapper = new ObjectMapper();
		TestClass value = mapper.readValue(json(), TestClass.class);
		assertEquals(testPnr, value.getPnr().getOriginalId());
	}

	private String json() {
		return "{\"pnr\":{\"type\":\""+PersonalIdentityNumberSE.class.getCanonicalName()+"\",\"value\":\""+testPnr+"\"}}";
	}
	
	private TestClass testClass() throws InvalidIdException {
		TestClass testClass = new TestClass();
		testClass.setPnr(new PersonalIdentityNumberSE(testPnr));
		return testClass;
	}
	
	private static class TestClass{
		@JsonSerialize(using=ValidIDJsonSerializer.class)
		@JsonDeserialize(using=ValidIDJsonDeserializer.class)
		private PersonalIdentityNumberSE pnr;

		public PersonalIdentityNumberSE getPnr() {
			return pnr;
		}

		public void setPnr(PersonalIdentityNumberSE pnr) {
			this.pnr = pnr;
		}
	}
}
