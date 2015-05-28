/**
 * 
 */
package se.face.entityids.model.serialize;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import se.face.entityids.model.ValidID;
import se.face.entityids.model.factory.IDFactory;

/**
 * @author Samuel
 *
 */
public class ValidIDXmlAdapter extends XmlAdapter<ValidIDXmlAdapter.XmlValidID, ValidID>{

	@Override
	public XmlValidID marshal(ValidID validID) throws Exception {
		XmlValidID xmlValidID = new XmlValidID();
		xmlValidID.value = validID.getOriginalId();
		xmlValidID.type = validID.getClass().getCanonicalName();
		return xmlValidID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidID unmarshal(XmlValidID xmlValidID) throws Exception {
		return IDFactory.create(xmlValidID.value, (Class<ValidID>) Class.forName(xmlValidID.type));
	}

	public static class XmlValidID {
		@XmlAttribute
		private String type;
		
		@XmlValue
		private String value;
	}
}
