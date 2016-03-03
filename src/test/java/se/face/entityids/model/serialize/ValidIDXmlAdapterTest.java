/**
 * 
 */
package se.face.entityids.model.serialize;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.PersonalIdentityNumberSE;

/**
 * @author Samuel Edqvist
 *
 */
public class ValidIDXmlAdapterTest {
	private JAXBContext jaxbContext;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	@Before
	public void setUp() throws JAXBException{
		jaxbContext = JAXBContext.newInstance(TestClass.class);
		marshaller = jaxbContext.createMarshaller();
		unmarshaller = jaxbContext.createUnmarshaller();
	}
	
	@Test
	public void shouldMarshall() throws InvalidIdException, JAXBException{
		final String pnr ="194301013787";
		TestClass testObject = new TestClass();
		testObject.setStr("apa");
		testObject.setPnr(new PersonalIdentityNumberSE(pnr));

		StringWriter writer = new StringWriter();
		marshaller.marshal(testObject, writer);
		String xml = writer.getBuffer().toString();
		assertThat(xml, StringContains.containsString(
				"type=\""+PersonalIdentityNumberSE.class.getCanonicalName()+"\">"+pnr));
	}
	
	@Test
	public void shouldUnmarshall() throws InvalidIdException, JAXBException{
		StringReader stringReader = new StringReader(xml());
		TestClass testObject = (TestClass) unmarshaller.unmarshal(stringReader);
		assertNotNull(testObject);
		assertEquals("19430101-3787", testObject.getPnr().getOriginalId());
		assertEquals("somestr", testObject.getStr());
	}
	
	private String xml() {
		return "<testClass>"+
					"<str>somestr</str>"+
					"<pnr type=\""+PersonalIdentityNumberSE.class.getCanonicalName()+"\">19430101-3787</pnr>"+
			   "</testClass>";
	}

	@XmlRootElement(name = "testClass")
	@XmlAccessorType(XmlAccessType.FIELD)
	private static class TestClass{
		private String str;
		
		@XmlJavaTypeAdapter(ValidIDXmlAdapter.class)
		private PersonalIdentityNumberSE pnr;
		
		public PersonalIdentityNumberSE getPnr() {
			return pnr;
		}
		public void setPnr(PersonalIdentityNumberSE pnr) {
			this.pnr = pnr;
		}
		public String getStr() {
			return str;
		}
		public void setStr(String str) {
			this.str = str;
		}
	}
}
