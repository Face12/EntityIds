/**
 * 
 */
package se.face.entityids.model.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;
import se.face.entityids.model.impl.enumtypes.PersonalIdentityNumberSEError;
import se.face.entityids.validation.Modul10;
/**
 * Valid id class for swedish personal identity numbers
 * @author Samuel
 *
 */
public class PersonalIdentityNumberSE extends ValidID{	
	private static final Pattern validFormatPattern = 
			Pattern.compile("^\\d{2}+\\d{6}+[- ]?+\\d{4}+$");

	/**
	 * Creates new PersonalIdentityNumbers that must conform with this pattern:
	 * yyyyMMdd[ -]?nnnn
	 * it must also have a valid check digit.
	 * @param id
	 * @throws InvalidIdException - If invalid. The error code will be one of {@link PersonalIdentityNumberSEError}
	 */
	public PersonalIdentityNumberSE(String id) throws InvalidIdException {
		super(id);
	}

	@Override
	protected void validate() throws InvalidIdException {
		Matcher matcher = validFormatPattern.matcher(getOriginalId());
		if (!matcher.find()){
			throw new InvalidIdException(PersonalIdentityNumberSEError.WRONG_FORMAT.getCode());
		}
		final Date date = getBirthDate();
		
		if (date == null || date.after(new Date())){
			throw new InvalidIdException(PersonalIdentityNumberSEError.WRONG_DATE.getCode());
		}
		
		if (!Modul10.checkModul10(getBaseId())){
			throw new InvalidIdException(PersonalIdentityNumberSEError.WRONG_CHECKDIGIT.getCode());
		}
	}

	/**
	 * The personal number with number only.
	 */
	@Override
	protected String createNormalizedId() {
		return normalize(getOriginalId());
	}

	/**
	 * The personal number without century and only numbers
	 */
	public String getBaseId() {
		return getNormalizedId().substring(2);
	}
	
	/**
	 * The personal number in the form yyyyMMdd-nnnn
	 */
	public String getFormattedWithDash(){
		final String normalizedId = getNormalizedId();
		return normalizedId.substring(0, 8)+"-"+normalizedId.substring(8);
	}
	
	/**
	 * The birth date according to the personal number
	 */
	public Date getBirthDate(){
		final String dateString = getNormalizedId().substring(0, 8);
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String normalize(String id) {
		return id.replaceAll("[ -]", "");
	}
}
