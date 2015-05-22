/**
 * 
 */
package se.face.entityids.model;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;

/**
 * @author Samuel
 *
 */
public class ValidIDTest {
	@Test
	public void shouldValidateConstructorCall() throws InvalidIdException{
		new ValidIDAllwaysValid("123");
	}
	
	@Test
	public void shouldValidateNewInstance(){
		assertNotNull(ValidID.create("123", ValidIDAllwaysValid.class));
	}
	
	@Test
	public void shouldValidateIsValid(){
		assertTrue(ValidID.isValid("123", ValidIDAllwaysValid.class));
	}
	
	@Test(expected = InvalidIdException.class)
	public void shouldNotValidateConstructorCall() throws InvalidIdException{
		new ValidIDNeverValid("123");
	}
	
	@Test
	public void shouldNotValidateNewInstance(){
		assertNull(ValidID.create("123", ValidIDNeverValid.class));
	}
	
	@Test
	public void shouldNotValidateIsValid(){
		assertFalse(ValidID.isValid("123", ValidIDNeverValid.class));
	}
	
	@Test
	public void shouldNormalize(){
		assertEquals("123", ValidID.create("12 3", ValidIDAllwaysValid.class).getNormalizedId());
	}
	
	private static class ValidIDAllwaysValid extends ValidID{
		public ValidIDAllwaysValid(String id) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
			//Allways valid
		}
		@Override
		public String getNormalizedId(){
			return getOriginalId().replaceAll(" ", "");
		}
	}	
	private static class ValidIDNeverValid extends ValidID{
		public ValidIDNeverValid(String id) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
			throw new InvalidIdException("Never valid");
		}
		@Override
		public String getNormalizedId(){
			return null;
		}
	}
}
