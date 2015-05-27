/**
 * 
 */
package se.face.entityids.model.impl.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.test.TestDataArrays;

/**
 * @author Samuel
 *
 */
public class CompanyRegistrationNumberFactoryTest {
	
	@Test
	public void shouldGuessAndBeValid(){
		for (String[] idAndCentury: 
				TestDataArrays.PINSE_GUESSABLE_AND_VALID_NO_CENTURY_IDS_AND_EXPECTED_CENTURY){
			try {
				CompanyRegistrationNumberSEFactory.createWithIdCenturyGuess(idAndCentury[0]);
			} catch (InvalidIdException e) {
				fail(idAndCentury[0]+" should be guessable");
			}
		}
	}
	
	@Test
	public void crnShouldBeValidNoGuessNeeded(){		
		for (String id: 
			TestDataArrays.CRNSE_NON_PERSON_VALID){
			try {
				CompanyRegistrationNumberSEFactory.createWithIdCenturyGuess(id);
			} catch (InvalidIdException e) {
				fail(id+" should be a valid company registration number (no century guess needed)");
			}
		}
	}
	@Test
	public void shouldBeInvalidWithGuess(){
		for (String[] idAndReason: 
			TestDataArrays.PINSE_INVALID_AFTER_GUESS_NO_CENTURY_IDS){
			try {
				CompanyRegistrationNumberSEFactory.createWithIdCenturyGuess(idAndReason[0]);
				fail(idAndReason[0]+" should be invalid");
			} catch (InvalidIdException e) {
			}
		}
	}
	
	@Test
	public void shouldBeInvalidNoGuessNeeded(){
		for (String id: 
			TestDataArrays.arrayConcat(
					TestDataArrays.CRNSE_NON_PERSON_INVALID_CHECK_DIGIT,
					TestDataArrays.PINSE_INVALID_CHECK_DIGIT
					)){
			try {
				CompanyRegistrationNumberSEFactory.createWithIdCenturyGuess(id);
				fail(id+" should be invalid");
			} catch (InvalidIdException e) {
			}
		}
	}
}
