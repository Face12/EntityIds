/**
 * 
 */
package se.face.entityids.model.impl.factory;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.CompanyRegistrationNumberSE;

/**
 * @author Samuel
 *
 */
public final class CompanyRegistrationNumberSEFactory {
	private CompanyRegistrationNumberSEFactory(){}
	
	public static CompanyRegistrationNumberSE tryCreateWithIdCenturyGuess(String id){
		try {
			return createWithIdCenturyGuess(id);
		} catch (InvalidIdException e) {
			return null;
		}
	}
	
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
