/**
 * 
 */
package se.face.entityids.model.impl.factory;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.CompanyRegistrationNumberSE;

/**
 * @author Samuel Edqvist
 *
 */
public final class CompanyRegistrationNumberSEFactory {
	private CompanyRegistrationNumberSEFactory(){}
	
	/**
	 * Like createWithIdCenturyGuess but does not throw exception, returns null if invalid.
	 */
	public static CompanyRegistrationNumberSE tryCreateWithIdCenturyGuess(String id){
		try {
			return createWithIdCenturyGuess(id);
		} catch (InvalidIdException e) {
			return null;
		}
	}
	/**
	 * Creates a new registration number. If it's invalid a guess of century will be made 
	 * using {@link PersonalIdentityNumberSEFactory}.
	 * 
	 * @param id
	 * @return
	 * @throws InvalidIdException if the id is not valid even after century guess
	 */
	public static CompanyRegistrationNumberSE createWithIdCenturyGuess(String id) throws InvalidIdException{
		try{
			return new CompanyRegistrationNumberSE(id);
		}
		catch (InvalidIdException e){
		}
		return new CompanyRegistrationNumberSE(
				PersonalIdentityNumberSEFactory
					.createWithIdCenturyGuess(id)
					.getOriginalId());
	}
}
