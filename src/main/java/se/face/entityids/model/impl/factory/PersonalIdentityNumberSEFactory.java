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
import se.face.entityids.model.impl.enumtypes.IdentityNumberError;

/**
 * @author Samuel Edqvist
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
	 * If it doesn't contain century, it will be assumed to be the current if applicable 
	 * else the previous century will be used.
	 * @param id
	 * @return
	 * @throws InvalidIdException if the id is not valid even after century guess
	 */
	public static PersonalIdentityNumberSE createWithIdCenturyGuess(String id) throws InvalidIdException{
		final int currentCentury = Calendar.getInstance().get(Calendar.YEAR)/100;
		final String normalized = PersonalIdentityNumberSE.normalize(id);
		if (normalized.length() == 10){
			final String sixDigitDate = normalized.substring(0, 6);
			final String fourLast = normalized.substring(6);
			if (dateIsValidAndNonFuture(currentCentury, sixDigitDate)){
				return new PersonalIdentityNumberSE(currentCentury+sixDigitDate+fourLast);
			}
			else if (dateIsValidAndNonFuture(currentCentury-1, sixDigitDate)){
				return new PersonalIdentityNumberSE((currentCentury-1)+sixDigitDate+fourLast);
			}
			throw new InvalidIdException(IdentityNumberError.WRONG_DATE.getCode());
			
		}
		else if (normalized.length() == 12){
			return new PersonalIdentityNumberSE(id);
		}
		throw new InvalidIdException(IdentityNumberError.WRONG_FORMAT.getCode());
	}

	private static boolean dateIsValidAndNonFuture(final int century, final String sixDigitDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dateFormat.setLenient(false);
		Date now = new Date();
		try{
			Date date = dateFormat.parse(century+sixDigitDate);
			return date.before(now);
		} catch (ParseException e){
			return false;
		}
	}
}
