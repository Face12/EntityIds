/**
 * 
 */
package se.face.entityids.model.impl.enumtypes;

/**
 * @author Samuel Edqvist
 *
 */
public enum IdentityNumberError {
	OK(0),
	WRONG_FORMAT(1),
	WRONG_DATE(2),
	WRONG_CHECKDIGIT(3),
	;
	private final int code;
	
	private IdentityNumberError(final int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static IdentityNumberError byCode(int code){
		for (IdentityNumberError error : values()){
			if (error.code == code){
				return error;
			}
		}
		return null;
	}
}