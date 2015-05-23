/**
 * 
 */
package se.face.entityids.model.impl.factory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.impl.PersonalIdentityNumberSE;

/**
 * @author samweisz
 *
 */
public class PersonalIdentiyNumberSEFactoryTest {
	
	private static final String[][] guessableAndValidNoCenturyIdsAndExpectedCentury =
			new String[][]{
							{"0001010107",	"20"},
							{"000101-0107",	"20"},
							{"000101 0107", "20"},
							{"200101 0103", "19"},
							{"8405140677",  "19"},
							{"840514-0677", "19"},
							{"840514 0677", "19"},
							{"8402290103",  "19"}//leap year
						};
	
	private static final String[][] invalidGuessesNoCenturyIds =
			new String[][]{
							{"8405320107",	"day is 32"},
							{"8404310108",	"it's april and day is 30"},
							{"8405140678", 	"wrong check digit"},
							{"0101010206", 	"wrong check digit"},
							{"DET12323010", "it contains letters"},
							{"000101010", "it's too short"}
						};
	
	@Test
	public void shouldGuessAndBeValid(){
		for (String[] idAndCentury: guessableAndValidNoCenturyIdsAndExpectedCentury){
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
		for (String[] idAndReason: invalidGuessesNoCenturyIds){
			try {
				PersonalIdentityNumberSEFactory.createWithIdCenturyGuess(idAndReason[0]);
				fail("\""+idAndReason[0]+"\" should be invalid since "+idAndReason[1]);
			} catch (InvalidIdException e) {
			}
		}
	}
}
