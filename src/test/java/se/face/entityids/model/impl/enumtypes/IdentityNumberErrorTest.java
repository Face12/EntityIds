/**
 * 
 */
package se.face.entityids.model.impl.enumtypes;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Samuel Edqvist
 *
 */
public class IdentityNumberErrorTest {
	@Test
	public void shouldFindByCode(){
		assertEquals(IdentityNumberError.OK, IdentityNumberError.byCode(0));
	}
	
	@Test
	public void shouldNotFindByCode(){
		assertNull(IdentityNumberError.byCode(-1));
	}
}
