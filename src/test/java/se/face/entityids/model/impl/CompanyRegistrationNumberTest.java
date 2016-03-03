/**
 * 
 */
package se.face.entityids.model.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.factory.IDFactory;
import se.face.entityids.model.impl.enumtypes.IdentityNumberError;
import se.face.entityids.test.TestDataArrays;

/**
 * @author Samuel Edqvist
 *
 */
public class CompanyRegistrationNumberTest {
	private CompanyRegistrationNumberSE validCompany = IDFactory.create("555555-5555", CompanyRegistrationNumberSE.class);
	private CompanyRegistrationNumberSE validPerson = IDFactory.create("19430101-3787", CompanyRegistrationNumberSE.class);
	@Test
	public void shouldBeValidAndNotPerson(){
		for (String id : TestDataArrays.CRNSE_NON_PERSON_VALID){
			try {
				CompanyRegistrationNumberSE companyRegistrationNumberSE = new CompanyRegistrationNumberSE(id);
				assertFalse(id+"should not be a person", companyRegistrationNumberSE.isPersonalIdentityNumber());
			} catch (InvalidIdException e) {
				fail("\""+id+"\" should be a valid company registration number");
			}
		}
	}
	
	@Test
	public void shouldBeValidPerson(){
		for (String id : TestDataArrays.PINSE_VALID_IDS){
			try {
				CompanyRegistrationNumberSE companyRegistrationNumberSE = new CompanyRegistrationNumberSE(id);
				assertTrue(id+" should be a person", companyRegistrationNumberSE.isPersonalIdentityNumber());
				assertNotNull(companyRegistrationNumberSE.getPersonalIdentityNumberSE());
			} catch (InvalidIdException e) {
				fail("\""+id+"\" should be a valid company registration number (personal identification number)");
			}
		}
	}
	
	@Test
	public void shouldBeInvalidCheckDigit(){
		for (String id : TestDataArrays.CRNSE_NON_PERSON_INVALID_CHECK_DIGIT){
			assertEquals(IdentityNumberError.WRONG_CHECKDIGIT.getCode(), 
					IDFactory.validate(id, CompanyRegistrationNumberSE.class));
		}
	}
	
	@Test
	public void shouldBeInvalidFormat(){
		for (String[] idAndReason : TestDataArrays.PINSE_INVALID_FORMAT_AND_REASON){
			assertEquals(IdentityNumberError.WRONG_FORMAT.getCode(), 
					IDFactory.validate(idAndReason[0], CompanyRegistrationNumberSE.class));
		}
	}
	
	@Test
	public void shouldGet10Digit(){
		assertEquals("5555555555", validCompany.get10DigitId());
		assertEquals("4301013787", validPerson.get10DigitId());
	}
	
	@Test
	public void shouldGet12Digit(){
		assertEquals("165555555555", validCompany.get12DigitId());
		assertEquals("194301013787", validPerson.get12DigitId());
	}
	
	@Test
	public void shouldGetFormattedWithDash(){
		assertEquals("555555-5555", validCompany.getFormattedWithDash());
		assertEquals("19430101-3787", validPerson.getFormattedWithDash());
	}
	
	@Test
	public void shouldGet10DigitFormattedWithDash(){
		assertEquals("555555-5555", validCompany.get10DigitFormattedWithDash());
		assertEquals("430101-3787", validPerson.get10DigitFormattedWithDash());
	}
	
	@Test
	public void shouldNormalize(){
		assertEquals("5555555555", validCompany.getNormalizedId());
		assertEquals("194301013787", validPerson.getNormalizedId());
	}
	
}
