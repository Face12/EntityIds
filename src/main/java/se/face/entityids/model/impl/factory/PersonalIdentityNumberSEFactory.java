/**
 * 
 */
package se.face.entityids.model.impl.factory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.PersonalIdentityNumberSE;
import se.face.entityids.model.impl.enumtypes.PersonalIdentityNumberSEError;

/**
 * @author samweisz
 *
 */
public final class PersonalIdentityNumberSEFactory {

	private PersonalIdentityNumberSEFactory(){}
	
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
		final String normalized = PersonalIdentityNumberSE.normalize(id);
		if (normalized.length() == 10){
			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
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
}
