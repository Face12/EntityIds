/**
 * 
 */
package se.face.entityids.exception;

/**
 * @author Samuel
 *
 */
public class IDConstructionError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public IDConstructionError(String message){
		super(message);
	}
	
	public IDConstructionError(String message, Throwable t){
		super(message, t);
	}
}
