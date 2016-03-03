/**
 * 
 */
package se.face.entityids.model;

import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.factory.IDFactory;

/**
 * An ID that has not yet been validated. 
 * 
 * @author Samuel Edqvist
 *
 */
public class UnvalidatedID extends ID{
	private static final long serialVersionUID = 6639174321114321743L;
	
	protected UnvalidatedID(String id){
		super(id);
	}
	
	public <T extends ValidID> T validateAs(Class<T> validIdClass) throws InvalidIdException{
		return IDFactory.createValidated(getOriginalId(), validIdClass);
	}
}
