/**
 * 
 */
package se.face.entityids.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	
	@Test
	public void objectTest() throws InvalidIdException{
		ValidID validID1a = new ValidIDAllwaysValid("123");
		ValidID validID1b = new ValidIDAllwaysValid("123");
		ValidID validID2 = new ValidIDAllwaysValid("124");
		ValidID validIDNull1a = new ValidIDAllwaysValid(null);
		ValidID validIDNull1b = new ValidIDAllwaysValid(null);
		
		assertTrue("Should be equal", validID1a.equals(validID1a));
		assertTrue("Should be equal", validID1a.equals(validID1b));
		assertTrue("Should be equal", validIDNull1a.equals(validIDNull1b));
		assertFalse("Should not be equal", validID1a.equals(validID2));
		assertFalse("Should not be equal", validID1a.equals(validIDNull1a));
		assertFalse("Should not be equal", validIDNull1a.equals(validID1a));
		assertFalse("Should never be equal to null", validID1a.equals(null));
		assertFalse("Should never be equal to different class", validID1a.equals(""));
		assertTrue("Hashcode", validID1a.hashCode() == validID1b.hashCode());
	}
	
	@Test
	public void toStringTest() throws InvalidIdException{
		assertEquals("id=123", new ValidIDAllwaysValid("123").toString());
	}
}
