/**
 * 
 */
package se.face.entityids.model;

import se.face.entityids.exception.InvalidIdException;

/**
 * Represents an id that has been validated. Note that an instance of this class
 * can never hold an invalid id value, since it will be validated, using the validate() method,
 * at construction.
 * @author Samuel Edqvist
 *
 */
public abstract class ValidID extends ID{

	private static final long serialVersionUID = 6838249368920884295L;
	private final String normalizedId;
	
	protected ValidID(String id) throws InvalidIdException{
		super(id);
		normalizedId = createNormalizedId();
		validate();
	}
		
	public String getNormalizedId() {
		return normalizedId;
	}
	
	/**
	 * Validates the id according to the rules of the id type represented by this class.
	 * @throws InvalidIdException - If the id is invalid.
	 */
	protected abstract void validate() throws InvalidIdException;
	
	/**
	 * Creates a normalized string representation of this string.
	 */
	protected abstract String createNormalizedId();
	
	@Override
	public String toString() {
		return "id=" + getNormalizedId();
	}
}
