/**
 * 
 */
package se.face.entityids.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.model.factory.IDFactory;
import se.face.entityids.test.TestImplementationClasses.ValidIDAllwaysValid;
import se.face.entityids.test.TestImplementationClasses.ValidIDNeverValid;

/**
 * @author Samuel
 *
 */
public class IDFactoryTest {
	
	@Test
	public void shouldValidateNewInstance(){
		assertNotNull(IDFactory.create("123", ValidIDAllwaysValid.class));
	}
	
	@Test
	public void shouldValidateIsValid(){
		assertEquals(0, IDFactory.validate("123", ValidIDAllwaysValid.class));
	}
	
	@Test
	public void shouldNotValidateNewInstance(){
		assertNull(IDFactory.create("123", ValidIDNeverValid.class));
	}
	
	@Test
	public void shouldNotValidateIsValid(){
		assertNotEquals(0, IDFactory.validate("123", ValidIDNeverValid.class));
	}
}
