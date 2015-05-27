/**
 * 
 */
package se.face.entityids.model.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import se.face.entityids.exception.IDConstructionError;
import se.face.entityids.exception.InvalidIdException;
import se.face.entityids.model.ValidID;

/**
 * @author samweisz
 *
 */
public final class IDFactory {
	private IDFactory(){}
	
	/**
	 * Creates a new id and returns null if it is invalid. Sometimes more
	 * convenient to use than using a constructor that throws checked exception.
	 * @param id
	 * @return
	 */
	public static <T extends ValidID> T create(String id, Class<T> clazz){
		try {
			return createInternal(id, clazz);
		} catch (InvalidIdException e) {
			return null;
		}
	}
	
	/**
	 * Validating an id according to an id type.
	 * The id is considered invalid if the constructor of validIdClass
	 * throws an {@link InvalidIdException}, when used to construct an instance with id.
	 * 
	 * @return - the error code or 0 if valid
	 */
	public static int validate(String id, Class<? extends ValidID> validIdClass){
		try {
			createInternal(id, validIdClass);
			return 0;
		} catch (InvalidIdException e) {
			return e.getErrorCode();
		}
	}
	/**
	 * Checking if id is valid according to validIdClass.
	 * This is the same as validate(id, validIdClass) == 0.
	 */
	public static boolean isValid(String id, Class<? extends ValidID> validIdClass){
		return validate(id, validIdClass) == 0;
	}

	private static <T extends ValidID> T 
			createInternal(String id, Class<T> clazz) throws InvalidIdException {
		try {
			Constructor<T> constructor = clazz.getConstructor(String.class);
			return constructor.newInstance(id);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof InvalidIdException){
				throw (InvalidIdException) e.getTargetException();
			}
			throw new IDConstructionError("Constructor threw exception", e.getTargetException());
		} catch (NoSuchMethodException e) {
			throw new IDConstructionError("Found no suitable constructor for: "+clazz.getCanonicalName(), e);
		} catch (InstantiationException e) {
			throw new IDConstructionError("validIdClass must be a non-abstract subclass of: "+ValidID.class.getCanonicalName(),
					e);
		} catch (Exception e) {
			throw new IDConstructionError("Unknown error", e);
		}
	}
}
