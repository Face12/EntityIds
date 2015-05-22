/**
 * 
 */
package se.face.entityids.model.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;
import se.face.entityids.model.impl.PersonalIdentityNumberSE.PersonalIdentityNumberSEError;

/**
 * @author Samuel
 *
 */
public class PersonalIdentiyNumberSETest {
	private static final String[] validIds =
			new String[]{
							"190001010107", 
							"19000101-0107",
							"19000101 0107",
							"198405140677", 
							"19840514-0677",
							"19840514 0677",
							"198402290103", //leap year
							"200001010107",
							"200101010205"
						};
	
	private static final String[][] invalidFormatAndReason = 
			new String[][]{
							{"DET12323010",		"it contains letters"},
							{"19000101010",		"it's too short, only 11 digits"},
							{"19000101--0107",	"there are two dashes before last four digits"},
							{"1900-0101-0107",	"there is a dash after the year (and last four digits)"},
							{"1900-01010107",	"there is a dash after year"},
							{"1900 01010107",	"there is a blank after year"}
						};
	private static final String[][] invalidDateAndReason = 
			new String[][]{
							{"198405320107",	"day is 32"},
							{"198404310108",	"it's april and day is 30"},
							{"198404000108",	"day is zero"},
							{"198400010108",	"month is zero"},
							{"198413010108",	"month is 13"},
							{"190002290104",	"it's 29 February, but 1900 is not a leap year"},
							{"22000101-0107",	"it's a future date"}
						};
	
	private static final String[] invalidCheckDigit = 
			new String[]{
							"198405140678",
							"200101010206"
						};
	
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
	public void shouldBeValid(){
		for (String validId: validIds){
			assertTrue("\""+validId+"\" should be valid", 
					ValidID.isValid(validId, PersonalIdentityNumberSE.class));
		}
	}
	
	@Test
	public void shouldBeInvalidFormat(){
		for (String[] idAndReason: invalidFormatAndReason){
			testInvalid(idAndReason, PersonalIdentityNumberSEError.WRONG_FORMAT);
		}
	}
	
	@Test
	public void shouldBeInvalidDate(){
		for (String[] idAndReason: invalidDateAndReason){
			testInvalid(idAndReason, PersonalIdentityNumberSEError.WRONG_DATE);
		}
	}
	
	@Test
	public void shouldBeInvalidCheckDigit(){
		for (String id: invalidCheckDigit){
			testInvalid(id, "wrong check digit", PersonalIdentityNumberSEError.WRONG_CHECKDIGIT);
		}
	}
	
	@Test
	public void shouldGuessAndBeValid(){
		for (String[] idAndCentury: guessableAndValidNoCenturyIdsAndExpectedCentury){
			PersonalIdentityNumberSE personalNumber;
			try {
				personalNumber = PersonalIdentityNumberSE.createWithIdCenturyGuess(idAndCentury[0]);
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
				PersonalIdentityNumberSE.createWithIdCenturyGuess(idAndReason[0]);
				fail(errorMessage(idAndReason, "of any kind"));
			} catch (InvalidIdException e) {
			}
		}
	}
	
	private void testInvalid(String id, String reason, PersonalIdentityNumberSEError error) {
		testInvalid(new String[]{id, reason}, error);
	}

	private void testInvalid(String[] idAndReason, PersonalIdentityNumberSEError error) {
		try{
			new PersonalIdentityNumberSE(idAndReason[0]);
			fail(errorMessage(idAndReason, error.name()));
		}
		catch(InvalidIdException e){
			assertEquals(errorMessage(idAndReason, error.name()), error.getCode(), e.getErrorCode());
		}		
	}
	
	private String errorMessage(String[] idAndReason, String error){
		return "\""+idAndReason[0]+"\" should have error "+error+" since "+idAndReason[1];
	}
}
