/**
 * 
 */
package se.face.entityids.test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;

/**
 * @author samweisz
 *
 */
public class TestImplementationClasses {
	public static class ValidIDAllwaysValid extends ValidID{
		public ValidIDAllwaysValid(String id) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
			//Allways valid
		}
		@Override
		public String createNormalizedId(){
			return getOriginalId().replaceAll(" ", "");
		}
	}	
	public static class ValidIDNeverValid extends ValidID{
		public ValidIDNeverValid(String id) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
			throw new InvalidIdException("Never valid");
		}
		@Override
		public String createNormalizedId(){
			return null;
		}
	}
}
