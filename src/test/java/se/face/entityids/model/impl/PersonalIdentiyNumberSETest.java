/**
 * 
 */
package se.face.entityids.model.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import se.face.entityids.model.factory.IDFactory;
import se.face.entityids.model.impl.enumtypes.IdentityNumberError;
import se.face.entityids.test.TestDataArrays;

/**
 * @author Samuel
 *
 */
public class PersonalIdentiyNumberSETest {
	private PersonalIdentityNumberSE personalIdentityNumberSE = IDFactory.create("198405140677", PersonalIdentityNumberSE.class);
	
	@Test
	public void shouldBeValid(){
		for (String validId: TestDataArrays.PINSE_VALID_IDS){
			assertEquals("\""+validId+"\" should be valid", IdentityNumberError.OK.getCode(), 
					IDFactory.validate(validId, PersonalIdentityNumberSE.class));
		}
	}
	
	@Test
	public void shouldBeInvalidFormat(){
		for (String[] idAndReason: TestDataArrays.PINSE_INVALID_FORMAT_AND_REASON){
			testInvalid(idAndReason, IdentityNumberError.WRONG_FORMAT);
		}
	}
	
	@Test
	public void shouldBeInvalidDate(){
		for (String[] idAndReason: TestDataArrays.PINSE_INVALID_DATE_AND_REASON){
			testInvalid(idAndReason, IdentityNumberError.WRONG_DATE);
		}
	}
	
	@Test
	public void shouldBeInvalidCheckDigit(){
		for (String id: TestDataArrays.PINSE_INVALID_CHECK_DIGIT){
			testInvalid(id, "wrong check digit", IdentityNumberError.WRONG_CHECKDIGIT);
		}
	}
	
	@Test
	public void shouldGetBirthDate(){
		Date birthDate = personalIdentityNumberSE.getBirthDate();
		Calendar birthDateCalendar = Calendar.getInstance();
		birthDateCalendar.setTime(birthDate);
		assertEquals(1984, birthDateCalendar.get(Calendar.YEAR));
		assertEquals(4, birthDateCalendar.get(Calendar.MONTH));
		assertEquals(14, birthDateCalendar.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void shouldGet10Digit(){
		assertEquals("8405140677", personalIdentityNumberSE.get10DigitId());
	}
	
	@Test
	public void shouldGetFormattedWithDash(){
		assertEquals("19840514-0677", personalIdentityNumberSE.getFormattedWithDash());
	}
	
	private void testInvalid(String id, String reason, IdentityNumberError error) {
		testInvalid(new String[]{id, reason}, error);
	}

	private void testInvalid(String[] idAndReason, IdentityNumberError error) {
		assertEquals(errorMessage(idAndReason, error.name()), error.getCode(), 
				IDFactory.validate(idAndReason[0], PersonalIdentityNumberSE.class));	
	}
	
	private String errorMessage(String[] idAndReason, String error){
		return "\""+idAndReason[0]+"\" should have error "+error+" since "+idAndReason[1];
	}
}
