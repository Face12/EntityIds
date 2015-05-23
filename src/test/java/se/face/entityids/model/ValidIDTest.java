/**
 * 
 */
package se.face.entityids.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.test.TestImplementationClasses.ValidIDAllwaysValid;
import se.face.entityids.test.TestImplementationClasses.ValidIDNeverValid;

/**
 * @author samweisz
 *
 */
public class ValidIDTest {

	@Test
	public void shouldValidateConstructorCall() throws InvalidIdException{
		new ValidIDAllwaysValid("123");
	}
	
	@Test(expected = InvalidIdException.class)
	public void shouldNotValidateConstructorCall() throws InvalidIdException{
		new ValidIDNeverValid("123");
	}
	
	@Test
	public void shouldNormalize() throws InvalidIdException{
		assertEquals("123", new ValidIDAllwaysValid("12 3").getNormalizedId());
	}
}
