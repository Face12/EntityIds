/**
 * 
 */
package se.face.entityids.exception;

/**
 * @author Samuel
 *
 */
public class InvalidIdException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_ERROR_CODE = -1;
	
	private final int errorCode;
		
	public InvalidIdException(int errorCode){
		this.errorCode = errorCode;
	}
	
	public InvalidIdException(String message){
		this(message, DEFAULT_ERROR_CODE);
	}
	
	public InvalidIdException(String message, int errorCode){
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
