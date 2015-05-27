/**
 * 
 */
package se.face.entityids.model.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import se.face.entityids.exception.IDConstructionError;
import se.face.entityids.model.factory.IDFactory;
import se.face.entityids.test.TestImplementationClasses.ValidIDAbstract;
import se.face.entityids.test.TestImplementationClasses.ValidIDAllwaysValid;
import se.face.entityids.test.TestImplementationClasses.ValidIDHiddenConstructor;
import se.face.entityids.test.TestImplementationClasses.ValidIDNeverValid;
import se.face.entityids.test.TestImplementationClasses.ValidIDNoOnlyIdConstructor;
import se.face.entityids.test.TestImplementationClasses.ValidIDValidateThrowsRuntimeException;

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
	public void shouldValidate(){
		assertEquals(0, IDFactory.validate("123", ValidIDAllwaysValid.class));
	}
	
	@Test
	public void shouldValidateIsValid(){
		assertTrue(IDFactory.isValid("123", ValidIDAllwaysValid.class));
	}
	
	@Test
	public void shouldNotValidateNewInstance(){
		assertNull(IDFactory.create("123", ValidIDNeverValid.class));
	}
	
	@Test
	public void shouldNotValidate(){
		assertNotEquals(0, IDFactory.validate("123", ValidIDNeverValid.class));
	}
	
	@Test
	public void shouldNotValidateIsValid(){
		assertFalse(IDFactory.isValid("123", ValidIDNeverValid.class));
	}
	
	@Test(expected = IDConstructionError.class)
	public void shouldThrowExceptionWhenCreatingClassWithHiddenContructor(){
		IDFactory.create("123", ValidIDHiddenConstructor.class);
	}
	
	@Test(expected = IDConstructionError.class)
	public void shouldThrowExceptionWhenCreatingClassWithNoConstructorThatTakesOneStringOnly(){
		IDFactory.create("123", ValidIDNoOnlyIdConstructor.class);
	}
	
	@Test(expected = IDConstructionError.class)
	public void shouldThrowExceptionWhenCreatingClassWithAbstractClass(){
		IDFactory.create("123", ValidIDAbstract.class);
	}
	
	@Test(expected = IDConstructionError.class)
	public void shouldThrowExceptionWhenCreatingClassWhereValidateThrowsRuntimeException(){
		IDFactory.create("123", ValidIDValidateThrowsRuntimeException.class);
	}
}
