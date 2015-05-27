/**
 * 
 */
package se.face.entityids.model.impl.factory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.PersonalIdentityNumberSE;
import se.face.entityids.test.TestDataArrays;

/**
 * @author samweisz
 *
 */
public class PersonalIdentiyNumberSEFactoryTest {
	
	@Test
	public void shouldGuessAndBeValid(){
		for (String[] idAndCentury: TestDataArrays.PINSE_GUESSABLE_AND_VALID_NO_CENTURY_IDS_AND_EXPECTED_CENTURY){
			PersonalIdentityNumberSE personalNumber;
			try {
				personalNumber = PersonalIdentityNumberSEFactory.createWithIdCenturyGuess(idAndCentury[0]);
				assertTrue("Century should be "+idAndCentury[1], 
						personalNumber.getOriginalId().startsWith(idAndCentury[1]));
			} catch (InvalidIdException e) {
				e.printStackTrace();
				fail(idAndCentury[0]+" should be valid and the guessed century should be "+idAndCentury[1]);
			}
		}
	}
	
	@Test
	public void shouldBeInvalidWithGuess(){
		for (String[] idAndReason: TestDataArrays.PINSE_INVALID_AFTER_GUESS_NO_CENTURY_IDS){
			try {
				PersonalIdentityNumberSEFactory.createWithIdCenturyGuess(idAndReason[0]);
				fail("\""+idAndReason[0]+"\" should be invalid since "+idAndReason[1]);
			} catch (InvalidIdException e) {
			}
		}
	}
}
