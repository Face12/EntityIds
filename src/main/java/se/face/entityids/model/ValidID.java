/**
 * 
 */
package se.face.entityids.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import se.face.entityids.exception.InvalidIdException;

/**
 * Represents an id that has been validated. Note that an instance of this class
 * can never hold an invalid id value, since it will be validated, using the validate() method,
 * at construction.
 * @author Samuel
 *
 */
public abstract class ValidID {
	private final String originalId;
	
	protected ValidID(String id) throws InvalidIdException{
		this.originalId = id;
		validate();
	}
	
	public String getOriginalId() {
		return originalId;
	}
	
	/**
	 * Validates the id according to the rules of the id type represented by this class.
	 * @throws InvalidIdException - If the id is invalid.
	 */
	protected abstract void validate() throws InvalidIdException;
	
	/**
	 * Creates a normalized string representation of this string.
	 */
	public abstract String getNormalizedId();

	/**
	 * Creates a new id and returns null if it is invalid. Sometimes more
	 * convenient to use than using a constructor that throws checked exception.
	 * @param id
	 * @return
	 */
	public static <T extends ValidID> T create(String id, Class<T> clazz){
		try {
			Constructor<T> constructor = clazz.getConstructor(String.class);
			return constructor.newInstance(id);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof InvalidIdException){
				return null;
			}
			throw new RuntimeException(e);
		} catch (IllegalAccessException e){
			throw new RuntimeException("Contructor for: "+clazz.getCanonicalName()+" is not accessible", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Found no suitable constructor for: "+clazz.getCanonicalName(), e);
		} catch (InstantiationException e) {
			throw new RuntimeException("validIdClass must be a non-abstract subclass of: "+ValidID.class.getCanonicalName(),
					e);
		} catch (Exception e) {
			throw new RuntimeException("Unknown error", e);
		}
	}
	
	/**
	 * Validating an id according to an id type.
	 * The id is considered invalid if the constructor of validIdClass
	 * throws an {@link InvalidIdException}, when used to construct an instance with id.
	 */
	public static boolean isValid(String id, Class<? extends ValidID> validIdClass){
		return create(id, validIdClass) != null;
	}

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
