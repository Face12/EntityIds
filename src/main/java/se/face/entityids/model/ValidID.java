/**
 * 
 */
package se.face.entityids.model;

import java.io.Serializable;

import se.face.entityids.exception.InvalidIdException;

/**
 * Represents an id that has been validated. Note that an instance of this class
 * can never hold an invalid id value, since it will be validated, using the validate() method,
 * at construction.
 * @author Samuel
 *
 */
public abstract class ValidID implements Serializable{

	private static final long serialVersionUID = 6838249368920884295L;
	
	private final String originalId;
	private String normalizedId = null;
	
	protected ValidID(String id) throws InvalidIdException{
		this.originalId = id;
		validate();
	}
	
	public String getOriginalId() {
		return originalId;
	}
	
	public String getNormalizedId() {
		if (normalizedId == null){
			normalizedId = createNormalizedId();
		}
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

	@Override
	public int hashCode() {
		return originalId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValidID other = (ValidID) obj;
		if (originalId == null) {
			if (other.originalId != null) {
				return false;
			}
		} else if (!originalId.equals(other.originalId)) {
			return false;
		}
		return true;
	}
}
