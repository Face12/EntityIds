/**
 * 
 */
package se.face.entityids.model.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;
import se.face.entityids.validation.Modul10;
/**
 * Valid id class for swedish personal identity numbers
 * @author Samuel
 *
 */
public class PersonalIdentityNumberSE extends ValidID{	
	private static final Pattern validationPattern = 
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
	public void validate() throws InvalidIdException {
		Matcher matcher = validationPattern.matcher(getOriginalId());
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
	public String getNormalizedId() {
		return normalize(getOriginalId());
	}

	/**
	 * The personal number without century and only numbers
	 */
	public String getBaseId() {
		return getNormalizedId().substring(2);
	}
	
	/**
	 * The birth date according to the personal number
	 */
	public Date getBirthDate(){
		final String dateString = getNormalizedId().substring(0, 8);
		final DateFormat dateFormat = getDateFormat();
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Like createWithIdCenturyGuess but does not throw exception, returns null if invalid.
	 */
	public static PersonalIdentityNumberSE tryCreateWithIdCenturyGuess(String id){
		try {
			return createWithIdCenturyGuess(id);
		} catch (InvalidIdException e) {
			return null;
		}
	}
	
	/**
	 * Creating a personal identity number from an id that may or may not contain a century.
	 * The century will be assumed to be the current if applicable else last century will be used.
	 * @param id
	 * @return
	 * @throws InvalidIdException
	 */
	public static PersonalIdentityNumberSE createWithIdCenturyGuess(String id) throws InvalidIdException{
		Calendar now = Calendar.getInstance();
		final int currentCentury = Calendar.getInstance().get(Calendar.YEAR)/100;
		final String normalized = normalize(id);
		if (normalized.length() == 10){
			try{
				DateFormat dateFormat = getDateFormat();
				dateFormat.setLenient(false);
				final String sixDigitDate = normalized.substring(0, 6);
				final String fourLast = normalized.substring(6);
				int century=currentCentury;
				try{
					Date date = dateFormat.parse(century+sixDigitDate);
					if (date.after(now.getTime())){
						century = currentCentury-1;
					}
				} catch (ParseException e){
					century = currentCentury-1;
					try {
						dateFormat.parse(century+sixDigitDate);
					} catch (ParseException e1) {
						throw new InvalidIdException(PersonalIdentityNumberSEError.WRONG_DATE.getCode());
					}
				}
				return new PersonalIdentityNumberSE(century+sixDigitDate+fourLast);
			}
			catch (NumberFormatException e){
				throw new InvalidIdException(PersonalIdentityNumberSEError.WRONG_FORMAT.getCode());
			}
		}
		else if (normalized.length() == 12){
			return new PersonalIdentityNumberSE(id);
		}
		throw new InvalidIdException(PersonalIdentityNumberSEError.WRONG_FORMAT.getCode());
	}
	
	private static String normalize(String id) {
		return id.replaceAll("[ -]", "");
	}
	
	private static DateFormat getDateFormat() {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		simpleDateFormat.setLenient(false);
		return simpleDateFormat;
	}
	
	public enum PersonalIdentityNumberSEError {
		WRONG_FORMAT(1),
		WRONG_DATE(2),
		WRONG_CHECKDIGIT(3),
		;
		private final int code;
		
		private PersonalIdentityNumberSEError(final int code){
			this.code = code;
		}

		public int getCode() {
			return code;
		}
		
		public static PersonalIdentityNumberSEError byCode(int code){
			for (PersonalIdentityNumberSEError error : PersonalIdentityNumberSEError.values()){
				if (error.code == code){
					return error;
				}
			}
			return null;
		}
	}
}
