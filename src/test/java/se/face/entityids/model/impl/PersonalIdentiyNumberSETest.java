/**
 * 
 */
package se.face.entityids.model.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.model.factory.IDFactory;
import se.face.entityids.model.impl.enumtypes.PersonalIdentityNumberSEError;

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
	
	@Test
	public void shouldBeValid(){
		for (String validId: validIds){
			assertEquals("\""+validId+"\" should be valid", 0, 
					IDFactory.validate(validId, PersonalIdentityNumberSE.class));
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
	
	private void testInvalid(String id, String reason, PersonalIdentityNumberSEError error) {
		testInvalid(new String[]{id, reason}, error);
	}

	private void testInvalid(String[] idAndReason, PersonalIdentityNumberSEError error) {
		assertEquals(errorMessage(idAndReason, error.name()), error.getCode(), 
				IDFactory.validate(idAndReason[0], PersonalIdentityNumberSE.class));	
	}
	
	private String errorMessage(String[] idAndReason, String error){
		return "\""+idAndReason[0]+"\" should have error "+error+" since "+idAndReason[1];
	}
}
