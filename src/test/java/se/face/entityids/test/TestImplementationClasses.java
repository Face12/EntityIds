/**
 * 
 */
package se.face.entityids.test;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;

/**
 * @author Samuel Edqvist
 *
 */
@SuppressWarnings("serial")
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
	
	public static class ValidIDHiddenConstructor extends ValidID{
		protected ValidIDHiddenConstructor(String id) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
		}
		@Override
		public String createNormalizedId(){
			return null;
		}
	}
	
	public static class ValidIDNoOnlyIdConstructor extends ValidID{
		public ValidIDNoOnlyIdConstructor(String id, String add) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
		}
		@Override
		public String createNormalizedId(){
			return null;
		}
	}
	
	public abstract static class ValidIDAbstract extends ValidID{
		public ValidIDAbstract(String id) throws InvalidIdException {
			super(id);
		}
	}
	
	public static class ValidIDValidateThrowsRuntimeException extends ValidID{
		public ValidIDValidateThrowsRuntimeException(String id) throws InvalidIdException {
			super(id);
		}
		@Override
		public void validate() throws InvalidIdException {
			throw new RuntimeException();
		}
		@Override
		public String createNormalizedId(){
			return null;
		}
	}
}
